package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.TokenApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.TokenApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.common.web.api.BaseApi;

/**
 * Token発行API<br>
 * 各NodeAPIで事前に呼ばれる<br>
 *
 * @version 1.0.0
 */
@Component
public class TokenApi extends BaseApi<TokenApiRequest, TokenApiResponse> {

    @Override
    public TokenApiResponse getResponse() {
        return new TokenApiResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getApiName() {
        return NodeApiType.TOKEN.getApiNameType().getValue();
    }

    @Override
    public void bindErrorInfo(TokenApiResponse response, String errorMessage) {
        response.setResult(Result.FAILURE);
        response.setDetail(getApiName() + "に失敗しました. " + errorMessage);
    }

}
