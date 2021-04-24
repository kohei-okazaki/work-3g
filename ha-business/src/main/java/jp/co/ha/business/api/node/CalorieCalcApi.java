package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.CalorieCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.CalorieCalcApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.common.web.api.BaseApi;

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
public class CalorieCalcApi extends BaseApi<CalorieCalcApiRequest, CalorieCalcApiResponse> {

    /** カロリー計算APIの種別 */
    private static final NodeApiType TYPE = NodeApiType.CALORIE;

    @Override
    public CalorieCalcApiResponse getResponse() {
        return new CalorieCalcApiResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getApiName() {
        return TYPE.getApiNameType().getValue();
    }

    @Override
    public void bindErrorInfo(CalorieCalcApiResponse response) {
        response.setResult(Result.FAILURE);
        response.setDetail(getApiName() + "に失敗しました");
    }

}
