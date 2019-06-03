package jp.co.ha.business.api.service;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.web.service.BaseApiService;

/**
 * 健康情報登録サービスインターフェース
 *
 */
public interface HealthInfoRegistService
		extends BaseApiService<HealthInfoRegistRequest, HealthInfoRegistResponse> {

	/**
	 * リクエスト情報を健康情報に変換する
	 * 
	 * @param request
	 *     健康情報登録リクエスト
	 * @return 健康情報Entity
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfo toEntity(HealthInfoRegistRequest request) throws BaseException;

}
