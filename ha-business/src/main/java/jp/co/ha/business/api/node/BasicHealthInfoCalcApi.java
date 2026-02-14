package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.BasicHealthInfoCalcApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.common.web.api.BaseApi;

/**
 * 基礎健康情報計算API<br>
 * <ul>
 * <li>BMI</li>
 * <li>標準体重</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Component
public class BasicHealthInfoCalcApi
        extends BaseApi<BasicHealthInfoCalcApiRequest, BasicHealthInfoCalcApiResponse> {

    @Override
    public BasicHealthInfoCalcApiResponse getResponse() {
        return new BasicHealthInfoCalcApiResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getApiName() {
        return NodeApiType.BASIC.getApiNameType().getValue();
    }

    @Override
    public void bindErrorInfo(BasicHealthInfoCalcApiResponse response,
            String errorMessage) {
        response.setResult(Result.FAILURE);
        response.setDetail("%sに失敗しました. %s".formatted(getApiName(), errorMessage));
    }

}
