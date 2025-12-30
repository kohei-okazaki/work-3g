package jp.co.ha.business.api.track;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import jp.co.ha.business.api.track.request.ApiLogGetApiRequest;
import jp.co.ha.business.api.track.response.ApiLogGetApiResponse;
import jp.co.ha.business.api.track.response.BaseTrackApiResponse.TrackApiResult;
import jp.co.ha.business.api.type.ApiNameType;
import jp.co.ha.common.web.api.BaseApi;

/**
 * API通信ログ取得API
 *
 * @version 1.0.0
 */
@Component
public class ApiLogGetApi extends BaseApi<ApiLogGetApiRequest, ApiLogGetApiResponse> {

    @Override
    public ApiLogGetApiResponse getResponse() {
        return new ApiLogGetApiResponse();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

    @Override
    public String getApiName() {
        return ApiNameType.TRACK_API_API_LOG_GET.getValue();
    }

    @Override
    public void bindErrorInfo(ApiLogGetApiResponse response, String errorMessage) {
        response.setResult(TrackApiResult.FAILURE);
    }

}
