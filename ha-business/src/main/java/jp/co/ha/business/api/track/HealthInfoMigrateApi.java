package jp.co.ha.business.api.track;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.track.request.HealthInfoMigrateApiRequest;
import jp.co.ha.business.api.track.response.HealthInfoMigrateApiResponse;
import jp.co.ha.business.api.type.ApiNameType;
import jp.co.ha.common.web.api.BaseApi;

/**
 * 健康情報連携API
 *
 * @version 1.0.0
 */
@Component
public class HealthInfoMigrateApi
        extends BaseApi<HealthInfoMigrateApiRequest, HealthInfoMigrateApiResponse> {

    @Override
    public HealthInfoMigrateApiResponse getResponse() {
        return new HealthInfoMigrateApiResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getApiName() {
        return ApiNameType.TRACK_API_MIGRATE_HEALTH_INFO.getValue();
    }

    @Override
    public void bindErrorInfo(HealthInfoMigrateApiResponse response,
            String errorMessage) {
    }

}
