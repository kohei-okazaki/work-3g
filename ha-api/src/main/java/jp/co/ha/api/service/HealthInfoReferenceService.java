package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoReferenceRequest;
import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.common.api.BaseService;
import jp.co.ha.common.exception.HealthInfoException;

/**
 * 健康情報照会サービスインターフェース<br>
 *
 */
public interface HealthInfoReferenceService
		extends BaseService<HealthInfoReferenceRequest, HealthInfoReferenceResponse, HealthInfoException> {

}
