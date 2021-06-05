package jp.co.ha.batch.healthcheck;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsConfig;
import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.api.healthinfoapp.HealthCheckApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthCheckApiResponse;
import jp.co.ha.business.api.root.RootHealthCheckApi;
import jp.co.ha.business.api.root.request.RootHealthCheckApiRequest;
import jp.co.ha.business.api.root.response.RootHealthCheckApiResponse;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * ヘルスチェックAPIを実行するバッチ<br>
 * 以下のサーバが起動状態かどうかを確認する<br>
 * <ul>
 * <li>健康管理APIサーバ</li>
 * <li>管理者用APIサーバ</li>
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
    /** {@linkplain jp.co.ha.business.api.node.HealthCheckApi} */
    @Autowired
    @Qualifier("nodeHealthCheckApi")
    private jp.co.ha.business.api.node.HealthCheckApi nodeHealthCheckApi;
    /** {@linkplain RootHealthCheckApi} */
    @Autowired
    private RootHealthCheckApi rootHealthCheckApi;
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

        Long transactionId = apiCommunicationDataComponent.getTransactionId();
        // 健康管理API.ヘルスチェックAPI 呼び出し
        sendHealthCheckApi(transactionId);
        // NodeAPI.ヘルスチェックAPI 呼び出し
        sendNodeHealthCheckApi(transactionId);
        // 管理者用API.ヘルスチェックAPI 呼び出し
        sendRootHealthCheckApi(transactionId);

        return RepeatStatus.FINISHED;
    }

    /**
     * 管理者用API:ヘルスチェックAPI呼び出し
     *
     * @param transactionId
     *     トランザクションID
     * @throws BaseException
     *     APIの実行に失敗した場合
     */
    private void sendRootHealthCheckApi(Long transactionId) throws BaseException {

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getRootApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(rootHealthCheckApi.getApiName(), null, transactionId);

        RootHealthCheckApiResponse response = rootHealthCheckApi
                .callApi(new RootHealthCheckApiRequest(), apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResult()) {
        case SUCCESS:
            LOG.debug("root api server up");
            slackApiComponent.send(ContentType.BATCH, "root api server up");
            break;
        case FAILURE:
            LOG.error("root api server down");
            awsSesComponent.sendMail(awsConfig.getMailAddress(), "管理者用API.ヘルスチェックAPI結果",
                    TEMPLATE_ID);
            slackApiComponent.send(ContentType.BATCH, "root api server down");
            break;
        }
    }

    /**
     * NodeAPI:ヘルスチェックAPI呼び出し
     *
     * @param transactionId
     *     トランザクションID
     * @throws BaseException
     *     APIの実行に失敗した場合
     */
    private void sendNodeHealthCheckApi(Long transactionId) throws BaseException {

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthinfoNodeApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(nodeHealthCheckApi.getApiName(), null, transactionId);

        jp.co.ha.business.api.node.response.HealthCheckApiResponse response = nodeHealthCheckApi
                .callApi(new jp.co.ha.business.api.node.request.HealthCheckApiRequest(),
                        apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResult()) {
        case SUCCESS:
            LOG.debug("node api server up");
            slackApiComponent.send(ContentType.BATCH, "node api server up");
            break;
        case FAILURE:
            LOG.error("node api server down");
            awsSesComponent.sendMail(awsConfig.getMailAddress(), "NodeAPI ヘルスチェックAPI結果",
                    TEMPLATE_ID);
            slackApiComponent.send(ContentType.BATCH, "node api server down");
            break;
        }

    }

    /**
     * 健康管理API:ヘルスチェックAPI呼び出し
     *
     * @param transactionId
     *     トランザクションID
     * @throws BaseException
     *     APIの実行に失敗した場合
     */
    private void sendHealthCheckApi(Long transactionId) throws BaseException {

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthInfoApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(healthCheckApi.getApiName(), null, transactionId);

        HealthCheckApiResponse response = healthCheckApi
                .callApi(new HealthCheckApiRequest(), apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResultType()) {
        case SUCCESS:
            LOG.debug("healthinfo api server up");
            slackApiComponent.send(ContentType.BATCH, "healthinfo api server up");
            break;
        case FAILURE:
            LOG.error("healthinfo api server down");
            awsSesComponent.sendMail(awsConfig.getMailAddress(), "健康管理API ヘルスチェックAPI結果",
                    TEMPLATE_ID);
            slackApiComponent.send(ContentType.BATCH, "healthinfo api server down");
            break;
        }

    }

}
