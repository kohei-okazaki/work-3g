package jp.co.ha.batch.healthInfoMigrate;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.track.HealthInfoMigrateApi;
import jp.co.ha.business.api.track.request.HealthInfoMigrateApiRequest;
import jp.co.ha.business.api.track.response.HealthInfoMigrateApiResponse;
import jp.co.ha.business.component.ApiLogComponent;
import jp.co.ha.business.dto.ApiLogQueuePayload;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.api.ApiConnectInfo;

/**
 * 健康情報連携処理-Writer<br>
 * 健康情報連携APIを呼び出す
 * 
 * @version 1.0.0
 */
@Component
@StepScope
public class HealthInfoMigrateWriter implements ItemWriter<HealthInfoMigrateApiRequest> {

    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties prop;
    /** API通信ログComponent */
    @Autowired
    private ApiLogComponent apiLogComponent;
    /** 健康情報連携API */
    @Autowired
    private HealthInfoMigrateApi api;

    @Override
    public void write(Chunk<? extends HealthInfoMigrateApiRequest> chunk)
            throws Exception {
        sendHealthInfoMirgateApi(chunk.getItems());
    }

    /**
     * 健康情報連携APIを呼び出す
     * 
     * @param list
     *     健康情報リスト
     * @throws BaseException
     *     JSON変換に失敗した場合
     */
    private void sendHealthInfoMirgateApi(
            List<? extends HealthInfoMigrateApiRequest> list) throws BaseException {

        String transactionId = apiLogComponent.transactionId();

        ApiConnectInfo connectInfo = new ApiConnectInfo()
                .withUrlSupplier(() -> prop.trackApiUrl() + "healthinfo/");

        for (HealthInfoMigrateApiRequest request : list) {

            HealthInfoMigrateApiResponse response = api.callApi(request, connectInfo);

            ApiLogQueuePayload payload = apiLogComponent
                    .getPayload4TrackApi(api, connectInfo, request, response,
                            transactionId);
            apiLogComponent.registQueue(payload);
        }

    }

}
