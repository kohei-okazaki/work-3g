package jp.co.ha.batch.execute;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.api.aws.AwsConfig;
import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.api.healthcheck.HealthCheckApi;
import jp.co.ha.business.api.healthcheck.request.HealthCheckRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckResponse;
import jp.co.ha.business.db.crud.create.ApiCommunicationDataCreateService;
import jp.co.ha.business.db.crud.update.ApiCommunicationDataUpdateService;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;
import jp.co.ha.web.form.BaseRestApiResponse;

/**
 * 健康管理API_ヘルスチェックAPIを呼び出すバッチ
 *
 * @version 1.0.0
 */
@Component("healthCheckApiBatch")
public class HealthCheckApiBatch extends BaseBatch {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckApiBatch.class);
    /** ヘルスチェックAPIメールテンプレートID */
    private static final String TEMPLATE_ID = "mail-template/health-check-template.txt";

    /** API通信情報作成サービス */
    @Autowired
    private ApiCommunicationDataCreateService apiCommunicationDataCreateService;
    /** API通信情報更新サービス */
    @Autowired
    private ApiCommunicationDataUpdateService apiCommunicationDataUpdateService;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;
    /** ヘルスチェックAPI */
    @Autowired
    private HealthCheckApi healthCheckApi;
    /** AWS-SES */
    @Autowired
    private AwsSesComponent awsSesComponent;
    /** AWS個別設定情報 */
    @Autowired
    private AwsConfig awsConfig;

    @Override
    public BatchResult execute(CommandLine cmd) throws BaseException {

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthInfoApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = createApiCommunicationData(
                "ヘルスチェックAPI");

        HealthCheckResponse response = healthCheckApi.callApi(new HealthCheckRequest(),
                apiConnectInfo);

        // API通信情報を更新
        updateApiCommunicationData(apiCommunicationData, apiConnectInfo, response);

        switch (response.getResultType()) {
        case SUCCESS:
            LOG.debug("健康管理APIサーバが起動状態...");
            break;
        case FAILURE:
            LOG.error("健康管理APIサーバの状態が異常...");
            awsSesComponent.sendMail(awsConfig.getMailAddress(), "ヘルスチェックAPI結果",
                    TEMPLATE_ID);
            break;
        }
        return BatchResult.SUCCESS;
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        return options;
    }

    /**
     * API通信情報を登録する
     *
     * @param apiName
     *     API名
     * @return API通信情報
     */
    private ApiCommunicationData createApiCommunicationData(String apiName) {

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = new ApiCommunicationData();
        apiCommunicationData.setApiName(apiName);
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
