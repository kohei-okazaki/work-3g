package jp.co.ha.dashboard.healthinfo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.ha.business.api.healthinfo.HealthInfoRegistApi;
import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.healthinfo.type.TestMode;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoDto;
import jp.co.ha.business.healthInfo.service.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
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
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.api.ApiConnectInfo;

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
    /** 健康情報登録API */
    @Autowired
    private HealthInfoRegistApi registApi;
    /** messageSource */
    @Autowired
    private MessageSource messageSource;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addModel(Model model, HealthInfoDto dto, HealthInfo lastHealthInfo) {
        model.addAttribute("beforeWeight", lastHealthInfo.getWeight());
        model.addAttribute("diffWeight", getDiffWeight(dto, lastHealthInfo));
        model.addAttribute("resultMessage", getDiffMessage(dto, lastHealthInfo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFirstReg(String userId) throws BaseException {
        return healthInfoSearchService.getSelectCountByUserId(userId) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HealthInfoCsvDownloadModel> toModelList(List<HealthInfo> healthInfo) {
        return healthInfo.stream().map(e -> {
            HealthInfoCsvDownloadModel model = new HealthInfoCsvDownloadModel();
            BeanUtil.copy(e, model);
            return model;
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthInfoRegistResponse regist(HealthInfoDto dto, String userId)
            throws BaseException {

        HealthInfoRegistRequest request = new HealthInfoRegistRequest();
        BeanUtil.copy(dto, request);
        request.setTestMode(TestMode.DB_REGIST);

        // アカウント情報.APIキーを設定
        Account account = accountSearchService.findById(userId).get();

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withHeader("Api-Key", account.getApiKey())
                .withUrlSupplier(
                        () -> prop.getHealthInfoApiUrl() + userId + "/healthinfo");

        HealthInfoRegistResponse apiResponse = registApi.callApi(request,
                apiConnectInfo);

        return apiResponse;
    }

    @Override
    public void sendHealthInfoMail(HealthInfoRegistResponse apiResponse)
            throws BaseException {
        healthInfoMailService.sendHealthInfoMail(apiResponse);
    }

    /**
     * {@inheritDoc}
     */
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
     * 体重差メッセージを返す
     *
     * @param dto
     *     健康情報DTO
     * @param healthInfo
     *     健康情報
     * @return 体重差メッセージ
     */
    private String getDiffMessage(HealthInfoDto dto, HealthInfo healthInfo) {
        HealthInfoStatus status = healthInfoCalcService
                .getHealthInfoStatus(dto.getWeight(), healthInfo.getWeight());
        return messageSource.getMessage(status.getMessage(), null, Locale.getDefault());
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
