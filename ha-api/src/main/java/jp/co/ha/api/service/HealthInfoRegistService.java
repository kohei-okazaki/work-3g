package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.api.BaseService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.HealthInfoException;

/**
 * 健康情報登録サービス<br>
 *
 */
public interface HealthInfoRegistService
		extends BaseService<HealthInfoRegistRequest, HealthInfoRegistResponse, HealthInfoException> {

	/**
	 * 健康情報にリクエスト情報をつめる
	 * @param request
	 * @return
	 */
	HealthInfo toEntity(HealthInfoRegistRequest request);

	/**
	 * 健康情報Entityを健康情報レスポンスクラスに変換する<br>
	 * @param healthInfo
	 * @return
	 */
	HealthInfoRegistResponse toResponse(HealthInfo healthInfo);

}
