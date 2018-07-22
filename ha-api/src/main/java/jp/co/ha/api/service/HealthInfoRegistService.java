package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.api.BaseService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.BaseAppException;

/**
 * 健康情報登録サービス<br>
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
	 * @throws BaseAppException
	 */
	HealthInfo toEntity(HealthInfoRegistRequest request) throws BaseAppException;

	/**
	 * 健康情報Entityを健康情報登録レスポンスクラスに変換する<br>
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return
	 * @throws BaseAppException
	 */
	HealthInfoRegistResponse toResponse(HealthInfo healthInfo) throws BaseAppException;

}
