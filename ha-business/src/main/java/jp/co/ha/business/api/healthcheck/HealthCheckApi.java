package jp.co.ha.business.api.healthcheck;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthcheck.request.HealthCheckApiRequest;
import jp.co.ha.business.api.healthcheck.response.HealthCheckApiResponse;
import jp.co.ha.business.api.type.ApiNameType;
import jp.co.ha.common.web.api.BaseApi;
import jp.co.ha.common.web.form.BaseRestApiResponse.ErrorInfo;
import jp.co.ha.common.web.form.BaseRestApiResponse.ResultType;

/**
 * ヘルスチェックAPI
 *
 * @version 1.0.0
 */
@Component
public class HealthCheckApi extends BaseApi<HealthCheckApiRequest, HealthCheckApiResponse> {

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
        return ApiNameType.HEALTH_CHECK.getValue();
    }

    @Override
    public void bindErrorInfo(HealthCheckApiResponse response) {
        response.setResultType(ResultType.FAILURE);
        ErrorInfo errorInfo = new ErrorInfo();
        response.setErrorInfo(errorInfo);
    }

}
