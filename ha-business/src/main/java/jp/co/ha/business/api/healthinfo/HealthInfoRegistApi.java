package jp.co.ha.business.api.healthinfo;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
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
        extends BaseApi<HealthInfoRegistRequest, HealthInfoRegistResponse> {

    @Override
    public HealthInfoRegistResponse getResponse() {
        return new HealthInfoRegistResponse();
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
    public void bindErrorInfo(HealthInfoRegistResponse response) {
        response.setResultType(ResultType.FAILURE);
        ErrorInfo errorInfo = new ErrorInfo();
        response.setErrorInfo(errorInfo);
    }

}
