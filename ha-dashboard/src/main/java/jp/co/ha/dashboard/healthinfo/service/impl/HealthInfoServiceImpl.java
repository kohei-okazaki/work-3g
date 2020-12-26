package jp.co.ha.dashboard.healthinfo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.ha.business.api.healthinfo.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.healthinfo.type.TestMode;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.business.dto.HealthInfoDto;
import jp.co.ha.business.exception.BusinessErrorCode;
import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.ApiException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.CsvConfig.CsvConfigBuilder;
import jp.co.ha.common.io.file.csv.CsvFileChar;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoMailService;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoService;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.api.ApiConnectInfo;
import jp.co.ha.web.form.BaseRestApiResponse;
import jp.co.ha.web.form.BaseRestApiResponse.ResultType;

/**
 * 健康情報登録画面サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoServiceImpl implements HealthInfoService {

    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** 健康情報メールService */
    @Autowired
    private HealthInfoMailService healthInfoMailService;
    /** 健康情報計算サービス */
    @Autowired
    private HealthInfoCalcService healthInfoCalcService;
    /** アカウント検索サービス */
    @Autowired
    private AccountSearchService accountSearchService;
    /** API通信情報作成サービス */
    @Autowired
    private ApiCommunicationDataCreateService apiCommunicationDataCreateService;
    /** API通信情報更新サービス */
    @Autowired
    private ApiCommunicationDataUpdateService apiCommunicationDataUpdateService;
    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi registApi;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;

    @Override
    public void addModel(Model model, HealthInfoDto dto, HealthInfo lastHealthInfo) {
        model.addAttribute("beforeWeight", lastHealthInfo.getWeight());
        model.addAttribute("diffWeight", getDiffWeight(dto, lastHealthInfo));
        model.addAttribute("resultMessage", getDiffStatus(dto, lastHealthInfo));
    }

    @Override
    public boolean isFirstReg(Integer seqUserId) throws BaseException {
        return healthInfoSearchService.getSelectCountBySeqUserId(seqUserId) == 0;
    }

    @Override
    public List<HealthInfoCsvDownloadModel> toModelList(List<HealthInfo> healthInfo) {
        return healthInfo.stream().map(e -> {
            HealthInfoCsvDownloadModel model = new HealthInfoCsvDownloadModel();
            BeanUtil.copy(e, model);
            return model;
        }).collect(Collectors.toList());
    }

    @Override
    public HealthInfoRegistResponse regist(HealthInfoDto dto, Integer seqUserId)
            throws BaseException {

        HealthInfoRegistRequest request = new HealthInfoRegistRequest();
        BeanUtil.copy(dto, request);
        request.setTestMode(TestMode.DB_REGIST);

        // アカウント情報.APIキーを設定
        Account account = accountSearchService.findById(seqUserId).get();

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withHeader("Api-Key", account.getApiKey())
                .withUrlSupplier(
                        () -> prop.getHealthInfoApiUrl() + seqUserId + "/healthinfo");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = createApiCommunicationData(
                registApi.getApiName(), seqUserId);

        HealthInfoRegistResponse apiResponse = registApi.callApi(request, apiConnectInfo);

        // API通信情報を更新
        updateApiCommunicationData(apiCommunicationData, apiConnectInfo, apiResponse);

        if (ResultType.SUCCESS != apiResponse.getResultType()) {
            // 健康情報登録APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.HEALTH_INFO_REGIST_API_CONNECT_ERROR,
                    apiResponse.getErrorInfo().getDetail());
        }

        return apiResponse;
    }

    @Override
    public void sendHealthInfoMail(HealthInfoRegistResponse apiResponse)
            throws BaseException {
        healthInfoMailService.sendHealthInfoMail(apiResponse);
    }

    @Override
    public CsvConfig getCsvConfig(HealthInfoFileSetting entity) throws BaseException {

        // ファイル名
        String fileName = "healthInfoRegist_"
                + DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDDHHMMSS_NOSEP)
                + FileExtension.CSV.getValue();
        // ファイル出力先
        String path = null;

        return new CsvConfigBuilder(fileName, path)
                .hasHeader(CommonFlag.TRUE.is(entity.getHeaderFlag()))
                .hasFooter(CommonFlag.TRUE.is(entity.getFooterFlag()))
                .csvFileChar(CsvFileChar.DOBBLE_QUOTE)
                .hasEnclosure(CommonFlag.TRUE.is(entity.getEnclosureCharFlag()))
                .useMask(CommonFlag.TRUE.is(entity.getMaskFlag()))
                .build();
    }

    /**
     * 体重差より健康情報ステータスを返す
     *
     * @param dto
     *     健康情報DTO
     * @param healthInfo
     *     健康情報
     * @return 健康情報ステータス
     */
    private String getDiffStatus(HealthInfoDto dto, HealthInfo healthInfo) {
        return healthInfoCalcService
                .getHealthInfoStatus(dto.getWeight(), healthInfo.getWeight()).getValue();
    }

    /**
     * 体重差を返す
     *
     * @param dto
     *     健康情報DTO
     * @param healthInfo
     *     健康情報
     * @return 体重差
     */
    private BigDecimal getDiffWeight(HealthInfoDto dto, HealthInfo healthInfo) {
        return healthInfoCalcService.calcDiffWeight(healthInfo.getWeight(),
                dto.getWeight());
    }

    /**
     * API通信情報を登録する
     *
     * @param apiName
     *     API名
     * @param seqUserId
     *     ユーザID
     * @return API通信情報
     */
    private ApiCommunicationData createApiCommunicationData(String apiName,
            Integer seqUserId) {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = new ApiCommunicationData();
        apiCommunicationData.setApiName(apiName);
        apiCommunicationData.setSeqUserId(seqUserId);
        apiCommunicationData.setRequestDate(DateTimeUtil.getSysDate());
        apiCommunicationDataCreateService.create(apiCommunicationData);

        return apiCommunicationData;
    }

    /**
     * API通信情報を更新する
     *
     * @param apiCommunicationData
     *     API通信情報
     * @param connectInfo
     *     API接続情報
     * @param response
     *     APIレスポンス情報
     */
    private void updateApiCommunicationData(ApiCommunicationData apiCommunicationData,
            ApiConnectInfo connectInfo, BaseRestApiResponse response) {

        apiCommunicationData.setHttpStatus(String.valueOf(connectInfo.getHttpStatus()));
        apiCommunicationData.setResult(response.getResultType().getValue());
        String detail = null;
        if (response.getErrorInfo() != null) {
            detail = response.getErrorInfo().getDetail();
        }
        apiCommunicationData.setDetail(detail);
        apiCommunicationData.setResponseDate(DateTimeUtil.getSysDate());
        apiCommunicationDataUpdateService.update(apiCommunicationData);

    }

}
