package jp.co.ha.business.api.healthinfo;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.healthinfo.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoRegistResponse;
import jp.co.ha.web.api.BaseApi;

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
        return "健康情報登録API";
    }

}
