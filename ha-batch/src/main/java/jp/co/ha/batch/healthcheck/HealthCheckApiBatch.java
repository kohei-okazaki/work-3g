package jp.co.ha.batch.healthcheck;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsConfig;
import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.api.healthcheck.HealthCheckApi;
import jp.co.ha.business.api.healthcheck.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckApiResponse;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * ヘルスチェックAPIを実行するバッチ<br>
 * 以下のサーバが起動状態かどうかを確認する<br>
 * <ul>
 * <li>健康管理APIサーバ</li>
 * </ul>
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class HealthCheckApiBatch implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckApiBatch.class);
    /** ヘルスチェックAPIメールテンプレートID */
    private static final String TEMPLATE_ID = "mail-template/health-check-template.txt";

    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain HealthInfoProperties} */
    @Autowired
    private HealthInfoProperties prop;
    /** {@linkplain HealthCheckApi} */
    @Autowired
    private HealthCheckApi healthCheckApi;
    /** {@linkplain AwsSesComponent} */
    @Autowired
    private AwsSesComponent awsSesComponent;
    /** {@linkplain AwsConfig} */
    @Autowired
    private AwsConfig awsConfig;
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApiComponent;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthInfoApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(healthCheckApi.getApiName(), null,
                        apiCommunicationDataComponent.getTransactionId());

        HealthCheckApiResponse response = healthCheckApi
                .callApi(new HealthCheckApiRequest(), apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResultType()) {
        case SUCCESS:
            LOG.debug("healthinfo api server up");
            slackApiComponent.send(ContentType.BATCH, "健康管理APIサーバが起動状態...");
            break;
        case FAILURE:
            LOG.error("healthinfo api server down");
            awsSesComponent.sendMail(awsConfig.getMailAddress(), "ヘルスチェックAPI結果",
                    TEMPLATE_ID);
            slackApiComponent.send(ContentType.BATCH, "健康管理APIサーバの状態が異常...");
            break;
        }

        return RepeatStatus.FINISHED;
    }

}
