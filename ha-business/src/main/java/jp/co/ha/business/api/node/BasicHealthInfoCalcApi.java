package jp.co.ha.business.api.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.BasicHealthInfoCalcRequest;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcResponse;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.web.api.BaseNodeApi;

/**
 * 基礎健康情報計算API<br>
 * 身長と体重からBMIと標準体重を計算する
 *
 * @version 1.0.0
 */
@Component
public class BasicHealthInfoCalcApi
        extends BaseNodeApi<BasicHealthInfoCalcRequest, BasicHealthInfoCalcResponse> {

    /** NodeAPIの種別 */
    private static final NodeApiType TYPE = NodeApiType.BASIC;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;

    @Override
    public BasicHealthInfoCalcResponse getResponse() {
        return new BasicHealthInfoCalcResponse();
    }

    @Override
    public String getUrl() {
        return prop.getHealthInfoCalcUrl() + TYPE.getValue();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public NodeApiType getNodeApiType() {
        return TYPE;
    }

}
