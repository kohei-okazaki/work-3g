package jp.co.ha.business.api.root;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.root.request.HealthCheckApiRequest;
import jp.co.ha.business.api.root.response.BaseRootApiResponse.Result;
import jp.co.ha.business.api.root.response.HealthCheckApiResponse;
import jp.co.ha.business.api.root.type.RootApiType;
import jp.co.ha.common.web.api.BaseApi;

/**
 * 管理者用API.ヘルスチェックAPI<br>
 *
 * @version 1.0.0
 */
@Component("rootHealthCheckApi")
public class HealthCheckApi
        extends BaseApi<HealthCheckApiRequest, HealthCheckApiResponse> {

    /** ヘルスチェックAPI種別 */
    private static final RootApiType TYPE = RootApiType.HEALTH_CHECK;

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
    }

}
