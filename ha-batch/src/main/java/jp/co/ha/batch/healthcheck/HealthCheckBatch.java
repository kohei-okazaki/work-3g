package jp.co.ha.batch.healthcheck;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.api.aws.AwsSesComponent.MailTemplateKey;
import jp.co.ha.business.api.healthinfoapp.HealthCheckApi;
import jp.co.ha.business.api.healthinfoapp.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthCheckApiResponse;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SystemProperties;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * ヘルスチェックAPIを実行するバッチ<br>
 * 以下のサーバが起動状態かどうかを確認する<br>
 * <ul>
 * <li>健康管理APIサーバ</li>
 * <li>NodeAPIサーバ</li>
 * <li>RootAPIサーバ</li>
 * </ul>
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class HealthCheckBatch implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckBatch.class);
    /** ヘルスチェックAPIメールテンプレートID */
    private static final MailTemplateKey TEMPLATE_ID = MailTemplateKey.HEALTHINFO_CHECK_TEMPLATE;

    /** API通信情報Component */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties healthInfoProperties;
    /** システム設定ファイル情報 */
    @Autowired
    private SystemProperties systemProperties;
    /** 健康管理アプリAPIサーバヘルスチェックAPI */
    @Autowired
    private HealthCheckApi healthCheckApi;
    /** Node APIサーバヘルスチェックAPI */
    @Autowired
    @Qualifier("nodeHealthCheckApi")
    private jp.co.ha.business.api.node.HealthCheckApi nodeHealthCheckApi;
    /** Root APIサーバヘルスチェックAPI */
    @Autowired
    @Qualifier("rootHealthCheckApi")
    private jp.co.ha.business.api.root.HealthCheckApi rootHealthCheckApi;
    /** AWS S3 Component */
    @Autowired
    private AwsSesComponent ses;
    /** SlackComponent */
    @Autowired
    private SlackApiComponent slack;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        Long transactionId = apiCommunicationDataComponent.getTransactionId();
        // 健康管理API.ヘルスチェックAPI 呼び出し
        sendHealthCheckApi(transactionId);
        if (!healthInfoProperties.isHealthinfoNodeApiMigrateFlg()) {
            // NodeAPI.ヘルスチェックAPI 呼び出し
            sendNodeHealthCheckApi(transactionId);
        }
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

        jp.co.ha.business.api.root.request.HealthCheckApiRequest req = new jp.co.ha.business.api.root.request.HealthCheckApiRequest();
        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(
                        () -> healthInfoProperties.getRootApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(rootHealthCheckApi.getApiName(), transactionId,
                        rootHealthCheckApi.getHttpMethod(),
                        rootHealthCheckApi.getUri(apiConnectInfo, req), req);

        jp.co.ha.business.api.root.response.HealthCheckApiResponse response = rootHealthCheckApi
                .callApi(req, apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResult()) {
        case SUCCESS:
            LOG.debug("root api server up");
            slack.send(ContentType.BATCH, "root api server up");
            break;
        case FAILURE:
            LOG.error("root api server down");
            ses.sendMail(systemProperties.getSystemMailAddress(),
                    "管理者用API.ヘルスチェックAPI結果", TEMPLATE_ID);
            slack.send(ContentType.BATCH, "root api server down");
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

        jp.co.ha.business.api.node.request.HealthCheckApiRequest req = new jp.co.ha.business.api.node.request.HealthCheckApiRequest();
        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> healthInfoProperties.getHealthinfoNodeApiUrl()
                        + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(nodeHealthCheckApi.getApiName(), transactionId,
                        nodeHealthCheckApi.getHttpMethod(),
                        nodeHealthCheckApi.getUri(apiConnectInfo, req), req);

        jp.co.ha.business.api.node.response.HealthCheckApiResponse response = nodeHealthCheckApi
                .callApi(req, apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResult()) {
        case SUCCESS:
            LOG.debug("node api server up");
            slack.send(ContentType.BATCH, "node api server up");
            break;
        case FAILURE:
            LOG.error("node api server down");
            ses.sendMail(systemProperties.getSystemMailAddress(),
                    "NodeAPI ヘルスチェックAPI結果", TEMPLATE_ID);
            slack.send(ContentType.BATCH, "node api server down");
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

        HealthCheckApiRequest req = new HealthCheckApiRequest();
        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(
                        () -> healthInfoProperties.getHealthInfoApiUrl() + "healthcheck");

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(healthCheckApi.getApiName(), transactionId,
                        healthCheckApi.getHttpMethod(),
                        healthCheckApi.getUri(apiConnectInfo, req), req);

        HealthCheckApiResponse response = healthCheckApi.callApi(req, apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo,
                response);

        switch (response.getResultType()) {
        case SUCCESS:
            LOG.debug("healthinfo api server up");
            slack.send(ContentType.BATCH, "healthinfo api server up");
            break;
        case FAILURE:
            LOG.error("healthinfo api server down");
            ses.sendMail(systemProperties.getSystemMailAddress(),
                    "健康管理API ヘルスチェックAPI結果", TEMPLATE_ID);
            slack.send(ContentType.BATCH, "healthinfo api server down");
            break;
        }

    }

}
