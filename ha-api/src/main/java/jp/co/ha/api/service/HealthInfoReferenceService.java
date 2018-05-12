package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoReferenceRequest;
import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.common.api.BaseService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.HealthInfoException;

/**
 * 健康情報照会サービスインターフェース<br>
 *
 */
public interface HealthInfoReferenceService
		extends BaseService<HealthInfoReferenceRequest, HealthInfoReferenceResponse, HealthInfoException> {

	/**
	 * 健康情報Entityを健康情報照会レスポンスクラスに変換する<br>
	 * @param healthInfo
	 * @return
	 */
	HealthInfoReferenceResponse toResponse(HealthInfo healthInfo);
}
