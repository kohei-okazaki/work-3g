package jp.co.ha.business.api.service;

import java.util.function.Function;

import jp.co.ha.business.api.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.common.api.service.BaseService;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報照会サービスインターフェース
 *
 */
public interface HealthInfoReferenceService
		extends BaseService<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

	/**
	 * 健康情報Entityを健康情報照会レスポンスクラスに変換する関数を返す
	 *
	 * @return 健康情報照会レスポンス変換関数
	 */
	Function<HealthInfo, HealthInfoReferenceResponse> toResponse();
}
