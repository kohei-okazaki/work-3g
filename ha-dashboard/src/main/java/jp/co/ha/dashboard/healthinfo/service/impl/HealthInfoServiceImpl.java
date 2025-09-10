package jp.co.ha.dashboard.healthinfo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.ha.business.api.healthinfoapp.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.api.healthinfoapp.type.TestMode;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
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
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoMailService;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoService;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.User;

/**
 * 健康情報登録画面サービス実装クラス
 *
 * @version 1.0.0
 */
@Service
public class HealthInfoServiceImpl implements HealthInfoService {

    /** 健康情報情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** 健康情報メールサービス */
    @Autowired
    private HealthInfoMailService healthInfoMailService;
    /** 健康情報計算サービス */
    @Autowired
    private HealthInfoCalcService healthInfoCalcService;
    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;
    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi registApi;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;

    @Override
    public void addModel(Model model, HealthInfoDto dto, HealthInfo lastHealthInfo) {
        model.addAttribute("beforeWeight", lastHealthInfo.getWeight());
        model.addAttribute("diffWeight", getDiffWeight(dto, lastHealthInfo));
        model.addAttribute("resultMessage", getDiffStatus(dto, lastHealthInfo));
    }

    @Override
    public boolean isFirstReg(Long seqUserId) throws BaseException {
        return healthInfoSearchService.countByHealthInfoIdAndSeqUserId(null,
                seqUserId) == 0L;
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
    public HealthInfoRegistApiResponse regist(HealthInfoDto dto, Long seqUserId)
            throws BaseException {

        // ユーザ情報.APIキーを設定
        User user = userComponent.findById(seqUserId).get();

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withHeader(ApiConnectInfo.X_API_KEY, user.getApiKey())
                .withUrlSupplier(
                        () -> prop.getHealthInfoApiUrl() + seqUserId + "/healthinfo");

        // API通信情報.トランザクションIDを採番
        Long transactionId = apiCommunicationDataComponent.getTransactionId();

        HealthInfoRegistApiRequest request = new HealthInfoRegistApiRequest();
        BeanUtil.copy(dto, request);
        request.setTestMode(TestMode.DB_REGIST);
        request.setTransactionId(transactionId);

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(registApi.getApiName(), transactionId, registApi.getHttpMethod(),
                        registApi.getUri(apiConnectInfo, request), request);

        HealthInfoRegistApiResponse apiResponse = registApi.callApi(request,
                apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                apiResponse);

        if (ResultType.SUCCESS != apiResponse.getResultType()) {
            // 健康情報登録APIの処理が成功以外の場合
            throw new ApiException(BusinessErrorCode.HEALTH_INFO_REGIST_API_CONNECT_ERROR,
                    apiResponse.getErrorInfo().getDetail());
        }

        return apiResponse;
    }

    @Override
    public void sendHealthInfoMail(HealthInfoRegistApiResponse apiResponse)
            throws BaseException {
        healthInfoMailService.sendHealthInfoMail(apiResponse);
    }

    @Override
    public CsvConfig getCsvConfig(HealthInfoFileSetting entity) throws BaseException {

        // ファイル名
        String fileName = "healthInfoRegist_" + DateTimeUtil
                .toString(DateTimeUtil.getSysDate(), DateFormatType.YYYYMMDDHHMMSS_NOSEP)
                + FileExtension.CSV;
        // ファイル出力先
        String path = null;

        return new CsvConfigBuilder(fileName, path)
                .hasHeader(entity.getHeaderFlag())
                .hasFooter(entity.getFooterFlag())
                .csvFileChar(CsvFileChar.DOBBLE_QUOTE)
                .hasEnclosure(entity.getEnclosureCharFlag())
                .useMask(entity.getMaskFlag())
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

}
