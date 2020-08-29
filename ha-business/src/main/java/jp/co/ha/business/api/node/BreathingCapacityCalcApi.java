package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.BreathingCapacityCalcRequest;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcResponse;
import jp.co.ha.web.api.BaseApi;

/**
 * 肺活量計算API<br>
 * <ul>
 * <li>予測肺活量</li>
 * <li>肺活量%</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Component
public class BreathingCapacityCalcApi
        extends BaseApi<BreathingCapacityCalcRequest, BreathingCapacityCalcResponse> {

    /** 肺活量計算APIの種別 */
    private static final NodeApiType TYPE = NodeApiType.BREATHING_CAPACITY;

    @Override
    protected BreathingCapacityCalcResponse getResponse() {
        return new BreathingCapacityCalcResponse();
    }

    @Override
    protected HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    protected String getApiName() {
        return TYPE.getName();
    }

}
