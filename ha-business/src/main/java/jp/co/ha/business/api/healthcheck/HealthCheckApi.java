package jp.co.ha.business.api.healthcheck;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthcheck.request.HealthCheckRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckResponse;
import jp.co.ha.web.api.BaseApi;

/**
 * ヘルスチェックAPI
 *
 * @version 1.0.0
 */
@Component
public class HealthCheckApi extends BaseApi<HealthCheckRequest, HealthCheckResponse> {

    @Override
    public HealthCheckResponse getResponse() {
        return new HealthCheckResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getApiName() {
        return "ヘルスチェックAPI";
    }

}
