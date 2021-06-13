package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.BreathingCapacityCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.BreathingCapacityCalcApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.common.web.api.BaseApi;

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
        extends
        BaseApi<BreathingCapacityCalcApiRequest, BreathingCapacityCalcApiResponse> {

    @Override
    public BreathingCapacityCalcApiResponse getResponse() {
        return new BreathingCapacityCalcApiResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getApiName() {
        return NodeApiType.BREATHING_CAPACITY.getApiNameType().getValue();
    }

    @Override
    public void bindErrorInfo(BreathingCapacityCalcApiResponse response,
            String errorMessage) {
        response.setResult(Result.FAILURE);
        response.setDetail(getApiName() + "に失敗しました. " + errorMessage);
    }

}
