package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.HealthCheckApiRequest;
import jp.co.ha.business.api.node.response.BaseNodeApiResponse.Result;
import jp.co.ha.business.api.node.response.HealthCheckApiResponse;
import jp.co.ha.business.api.node.type.NodeApiType;
import jp.co.ha.common.web.api.BaseApi;

/**
 * ヘルスチェックAPI
 *
 * @version 1.0.0
 */
@Component("nodeHealthCheckApi")
public class HealthCheckApi
        extends BaseApi<HealthCheckApiRequest, HealthCheckApiResponse> {

    /** ヘルスチェックAPIの種別 */
    private static final NodeApiType TYPE = NodeApiType.HEALTH_CHECK;

    @Override
    public HealthCheckApiResponse getResponse() {
        return new HealthCheckApiResponse();
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
    public void bindErrorInfo(HealthCheckApiResponse response) {
        response.setResult(Result.FAILURE);
        response.setDetail(getApiName() + "に失敗しました");
    }

}
