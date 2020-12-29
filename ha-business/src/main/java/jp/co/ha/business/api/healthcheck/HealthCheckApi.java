package jp.co.ha.business.api.healthcheck;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthcheck.request.HealthCheckRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckResponse;
import jp.co.ha.business.api.type.ApiNameType;
import jp.co.ha.web.api.BaseApi;
import jp.co.ha.web.form.BaseRestApiResponse.ErrorInfo;
import jp.co.ha.web.form.BaseRestApiResponse.ResultType;

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
        return ApiNameType.HEALTH_CHECK.getValue();
    }

    @Override
    public void bindErrorInfo(HealthCheckResponse response) {
        response.setResultType(ResultType.FAILURE);
        ErrorInfo errorInfo = new ErrorInfo();
        response.setErrorInfo(errorInfo);
    }

}
