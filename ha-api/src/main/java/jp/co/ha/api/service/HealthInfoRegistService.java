package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.common.api.BaseService;
import jp.co.ha.common.exception.BaseException;

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
	 * @return
	 * @throws BaseException
	 */
	HealthInfo toEntity(HealthInfoRegistRequest request) throws BaseException;

	/**
	 * 健康情報Entityを健康情報登録レスポンスクラスに変換する<br>
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return
	 * @throws BaseException
	 */
	HealthInfoRegistResponse toResponse(HealthInfo healthInfo) throws BaseException;

}
