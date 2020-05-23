package jp.co.ha.api.healthinfo;

import java.net.URI;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jp.co.ha.api.BaseApiTest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoReferenceResponse;
import jp.co.ha.common.system.SystemConfig;

/**
 * 健康情報照会APIテストクラス
 *
 * @version 1.0.0
 */
public class HealthInfoRefenceApiTest extends BaseApiTest {

    /** API呼び出しクラス */
    @Autowired
    private RestTemplate client;
    /** システム設定情報 */
    @Autowired
    private SystemConfig systemConfig;

    /**
     * 照会APIのテストメソッド
     */
    @Test
    public void testReference() {
        try {
            URI uri = new URI(this.systemConfig.getBaseApiUrl() + "/healthInfoReference");

            // ヘッダ作成
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // TODO
            // src/test/resources配下のJSONファイルインスタンスを引数に渡すように、JSOM文字列を使わないようにする
            String json = "{\"requestType\":\"002\",\"userId\":\"master\",\"healthInfoId\":10,\"apiKey\":\"1bc073967f4c24f000d0fc34006cf8de83752a9d8fde68278b18c033bbb68d2c\"}";

            RequestEntity<?> requestEntity = new RequestEntity<>(json, headers,
                    HttpMethod.POST, uri);

            ResponseEntity<HealthInfoReferenceResponse> responseEntity = this.client
                    .exchange(requestEntity, HealthInfoReferenceResponse.class);

            LOG.info(responseEntity.getStatusCode().toString());

            HealthInfoReferenceResponse response = responseEntity.getBody();
            LOG.infoRes(response);

        } catch (Exception e) {
            LOG.error("APIの実行に失敗しました", e);
        }
    }

}
