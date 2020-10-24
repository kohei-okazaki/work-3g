package jp.co.ha.batch.execute;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.api.healthcheck.HealthCheckApi;
import jp.co.ha.business.api.healthcheck.request.HealthCheckRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckResponse;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
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

    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;
    /** ヘルスチェックAPI */
    @Autowired
    private HealthCheckApi healthCheckApi;

    @Override
    public BatchResult execute(CommandLine cmd) throws BaseException {

        ApiConnectInfo apiConnectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.getHealthInfoApiUrl() + "healthcheck");

        HealthCheckResponse response = healthCheckApi.callApi(new HealthCheckRequest(),
                apiConnectInfo);

        switch (response.getResultType()) {
        case SUCCESS:
            LOG.debug("健康管理APIサーバが起動状態...");
            break;
        case FAILURE:
            LOG.error("健康管理APIサーバの状態が異常...");
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
