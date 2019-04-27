package jp.co.ha.business.api.service;

import java.util.function.Function;

import jp.co.ha.business.api.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.web.service.BaseApiService;

/**
 * 健康情報照会サービスインターフェース
 *
 */
public interface HealthInfoReferenceService
		extends BaseApiService<HealthInfoReferenceRequest, HealthInfoReferenceResponse> {

	/**
	 * 健康情報Entityを健康情報照会レスポンスクラスに変換する関数を返す
	 *
	 * @return Function<HealthInfo, HealthInfoReferenceResponse>
	 */
	Function<HealthInfo, HealthInfoReferenceResponse> toResponse();
}
