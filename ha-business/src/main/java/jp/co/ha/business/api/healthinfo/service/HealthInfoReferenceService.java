package jp.co.ha.business.api.healthinfo.service;

import jp.co.ha.business.api.healthinfo.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.healthinfo.response.HealthInfoReferenceResponse;
import jp.co.ha.web.service.BaseUserAuthApiService;

/**
 * 健康情報照会サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoReferenceService extends
        BaseUserAuthApiService<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

}
