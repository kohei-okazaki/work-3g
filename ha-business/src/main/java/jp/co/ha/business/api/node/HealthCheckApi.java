package jp.co.ha.business.api.node;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.node.request.HealthCheckApiRequest;
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
        return NodeApiType.HEALTH_CHECK.getApiNameType().getValue();
    }

    @Override
    public void bindErrorInfo(HealthCheckApiResponse response, String errorMessage) {
        response.setResult(response.getResult());
        response.setDetail(getApiName() + "に失敗しました. " + errorMessage);
    }

}
