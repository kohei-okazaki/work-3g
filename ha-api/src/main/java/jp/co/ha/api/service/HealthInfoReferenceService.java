package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoReferenceRequest;
import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.common.api.service.BaseService;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報照会サービスインターフェース
 *
 */
public interface HealthInfoReferenceService
		extends BaseService<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

	/**
	 * 健康情報Entityを健康情報照会レスポンスクラスに変換する
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return 健康情報照会レスポンス
	 */
	HealthInfoReferenceResponse toResponse(HealthInfo healthInfo);
}
