package jp.co.ha.business.api.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jp.co.ha.business.api.node.request.BasicHealthInfoCalcRequest;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcResponse;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * 基礎健康情報計算API
 *
 * @version 1.0.0
 */
@Component
public class BasicHealthInfoCalcApi {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(BasicHealthInfoCalcApi.class);

    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;
    /** APIを通信するためのClient */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @param request
     *     基礎健康情報計算APIリクエスト
     * @return 基礎健康情報計算APIレスポンスクラス
     */
    public BasicHealthInfoCalcResponse execute(BasicHealthInfoCalcRequest request) {

        LOG.info("基礎健康情報計算API START");

        BasicHealthInfoCalcResponse response = null;
        try {
            String url = prop.getHealthInfoCalcUrl() + "healthinfo";
            response = restTemplate.postForObject(url,
                    request, BasicHealthInfoCalcResponse.class);

        } catch (Throwable e) {
            LOG.error("APIの実施に失敗しました", e);
        } finally {
            LOG.info("基礎健康情報計算API END");
        }

        return response;
    }

}
