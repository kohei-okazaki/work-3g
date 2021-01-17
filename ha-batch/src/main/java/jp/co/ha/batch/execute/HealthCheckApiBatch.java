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
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.db.entity.ApiCommunicationData;
import jp.co.ha.web.api.ApiConnectInfo;

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

    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
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
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slackApiComponent;

    @Override
    public BatchResult execute(CommandLine cmd) throws BaseException {

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthInfoApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(healthCheckApi.getApiName(), null);

        HealthCheckResponse response = healthCheckApi.callApi(new HealthCheckRequest(),
                apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResultType()) {
        case SUCCESS:
            LOG.debug("健康管理APIサーバが起動状態...");
            slackApiComponent.send(ContentType.BATCH, "健康管理APIサーバが起動状態...");
            break;
        case FAILURE:
            LOG.error("健康管理APIサーバの状態が異常...");
            awsSesComponent.sendMail(awsConfig.getMailAddress(), "ヘルスチェックAPI結果",
                    TEMPLATE_ID);
            slackApiComponent.send(ContentType.BATCH, "健康管理APIサーバの状態が異常...");
            break;
        }
        return BatchResult.SUCCESS;
    }

    @Override
    public Options getOptions() {
        Options options = new Options();
        return options;
    }

}
