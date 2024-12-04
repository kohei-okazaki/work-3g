package jp.co.ha.batch.healthInfoMigrateBatch;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.HealthInfoRegistApi;
import jp.co.ha.business.api.track.HealthInfoMigrateApi;
import jp.co.ha.business.api.track.request.HealthInfoMigrateApiRequest;
import jp.co.ha.business.api.track.response.HealthInfoMigrateApiResponse;
import jp.co.ha.business.component.ApiCommunicationDataComponent;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.web.api.ApiConnectInfo;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * 健康情報連携バッチ
 *
 * @version 1.0.0
 */
@StepScope
@Component
public class HealthInfoMigrateBatch implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(HealthInfoMigrateBatch.class);

    /** {@linkplain HealthInfoProperties} */
    @Autowired
    private HealthInfoProperties prop;
    /** {@linkplain ApiCommunicationDataComponent} */
    @Autowired
    private ApiCommunicationDataComponent apiCommunicationDataComponent;
    /** {@linkplain HealthInfoRegistApi} */
    @Autowired
    private HealthInfoMigrateApi api;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        sendHealthInfoMirgateApi();

        return RepeatStatus.FINISHED;
    }

    /**
     * 健康情報連携APIを呼び出す
     */
    private void sendHealthInfoMirgateApi() {

        Long transactionId = apiCommunicationDataComponent.getTransactionId();

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getTrackApiUrl() + "healthinfo/");
        System.out.println("URL=" + apiConnectInfo.getUrlSupplier().get());

        // API通信情報を登録
        ApiCommunicationData apiCommunicationData = apiCommunicationDataComponent
                .create(api.getApiName(), null, transactionId);

        HealthInfoMigrateApiRequest request = new HealthInfoMigrateApiRequest();

        // TODO 実際の検索処理にする
        request.setSeqUserId(100L);
        HealthInfoMigrateApiRequest.HealthInfo healthInfo = new HealthInfoMigrateApiRequest.HealthInfo();
        healthInfo.setSeqHealthInfoId(new BigDecimal("99999"));
        healthInfo.setHeight(new BigDecimal("170.01"));
        healthInfo.setWeight(new BigDecimal("65.53"));
        healthInfo.setBmi(new BigDecimal("10.05"));
        healthInfo.setStandardWeight(new BigDecimal("60.09"));
        healthInfo.setCreatedAt(LocalDateTime.of(2000, 1, 5, 12, 50, 39));
        request.setHealthInfo(healthInfo);

        HealthInfoMigrateApiResponse response = api.callApi(request, apiConnectInfo);

        // API通信情報を更新
        apiCommunicationDataComponent.update(apiCommunicationData, apiConnectInfo);
    }

}
