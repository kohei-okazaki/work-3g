package jp.co.ha.business.api.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.CalorieCalcRequest;
import jp.co.ha.business.api.node.response.CalorieCalcResponse;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.web.api.BaseNodeApi;

/**
 * カロリー計算API<br>
 * <ul>
 * <li>基礎代謝量</li>
 * <li>1日の消費カロリー</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Component
public class CalorieCalcApi extends BaseNodeApi<CalorieCalcRequest, CalorieCalcResponse> {

    /** NodeAPIの種別 */
    private static final NodeApiType TYPE = NodeApiType.CALORIE;
    /** 健康情報関連プロパティ */
    @Autowired
    private HealthInfoProperties prop;

    @Override
    public CalorieCalcResponse getResponse() {
        return new CalorieCalcResponse();
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
