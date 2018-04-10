package jp.co.ha.api.service;

import jp.co.ha.api.exception.HealthInfoException;
import jp.co.ha.api.request.HealthInfoRequest;
import jp.co.ha.api.response.HealthInfoResponse;
import jp.co.ha.common.api.BaseApiService;

public interface HealthInfoService extends BaseApiService<HealthInfoRequest, HealthInfoResponse, HealthInfoException> {

}
