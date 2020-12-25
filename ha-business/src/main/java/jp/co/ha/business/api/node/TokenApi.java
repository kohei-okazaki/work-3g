package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.TokenRequest;
import jp.co.ha.business.api.node.response.BaseNodeResponse.Result;
import jp.co.ha.business.api.node.response.TokenResponse;
import jp.co.ha.web.api.BaseApi;

/**
 * Token発行API<br>
 * 各NodeAPIで事前に呼ばれる<br>
 *
 * @version 1.0.0
 */
@Component
public class TokenApi extends BaseApi<TokenRequest, TokenResponse> {

    /** トークン発行APIの種別 */
    private static final NodeApiType TYPE = NodeApiType.TOKEN;

    @Override
    public TokenResponse getResponse() {
        return new TokenResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getApiName() {
        return TYPE.getName();
    }

    @Override
    public void bindErrorInfo(TokenResponse response) {
        response.setResult(Result.FAILURE);
        response.setDetail(TYPE.getName() + "に失敗しました");
    }

}
