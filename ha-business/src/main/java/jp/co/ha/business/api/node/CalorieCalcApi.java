package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.CalorieCalcRequest;
import jp.co.ha.business.api.node.response.CalorieCalcResponse;
import jp.co.ha.web.api.BaseApi;

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
public class CalorieCalcApi extends BaseApi<CalorieCalcRequest, CalorieCalcResponse> {

    /** NodeAPIの種別 */
    private static final NodeApiType TYPE = NodeApiType.CALORIE;

    @Override
    public CalorieCalcResponse getResponse() {
        return new CalorieCalcResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getApiName() {
        return TYPE.getName();
    }

}
