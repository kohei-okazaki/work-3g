package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.api.service.BaseService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報登録サービスインターフェース<br>
 *
 */
public interface HealthInfoRegistService
		extends BaseService<HealthInfoRegistRequest, HealthInfoRegistResponse> {

	/**
	 * 健康情報にリクエスト情報をつめる<br>
	 *
	 * @param request
	 *     リクエスト
	 * @return HealthInfo
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfo toEntity(HealthInfoRegistRequest request) throws BaseException;

	/**
	 * 健康情報Entityを健康情報登録レスポンスクラスに変換する<br>
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return HealthInfoRegistResponse
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfoRegistResponse toResponse(HealthInfo healthInfo) throws BaseException;

}
