package jp.co.ha.business.api.healthinfoapp;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfoapp.request.HealthInfoRegistApiRequest;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.api.type.ApiNameType;
import jp.co.ha.common.web.api.BaseApi;
import jp.co.ha.common.web.form.BaseRestApiResponse.ErrorInfo;
import jp.co.ha.common.web.form.BaseRestApiResponse.ResultType;

/**
 * 健康情報登録API
 *
 * @version 1.0.0
 */
@Component
public class HealthInfoRegistApi
        extends BaseApi<HealthInfoRegistApiRequest, HealthInfoRegistApiResponse> {

    @Override
    public HealthInfoRegistApiResponse getResponse() {
        return new HealthInfoRegistApiResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getApiName() {
        return ApiNameType.HEALTH_INFO_REGIST.getValue();
    }

    @Override
    public void bindErrorInfo(HealthInfoRegistApiResponse response) {
        response.setResultType(ResultType.FAILURE);
        ErrorInfo errorInfo = new ErrorInfo();
        response.setErrorInfo(errorInfo);
    }

}
