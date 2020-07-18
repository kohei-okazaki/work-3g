package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.BasicHealthInfoCalcRequest;
import jp.co.ha.business.api.node.response.BasicHealthInfoCalcResponse;
import jp.co.ha.web.api.BaseApi;

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
        extends BaseApi<BasicHealthInfoCalcRequest, BasicHealthInfoCalcResponse> {

    /** NodeAPIの種別 */
    private static final NodeApiType TYPE = NodeApiType.BASIC;

    @Override
    public BasicHealthInfoCalcResponse getResponse() {
        return new BasicHealthInfoCalcResponse();
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
