package jp.co.ha.business.api.service;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.function.ThrowableFunction;
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
	 * @return ThrowableFunction<HealthInfoRegistRequest, HealthInfo>
	 * @throws BaseException
	 *     基底例外
	 */
	ThrowableFunction<HealthInfoRegistRequest, HealthInfo> toEntity() throws BaseException;

	/**
	 * 健康情報Entityを健康情報登録レスポンスクラスに変換する
	 *
	 * @return ThrowableFunction<HealthInfo, HealthInfoRegistResponse>
	 * @throws BaseException
	 *     基底例外
	 */
	ThrowableFunction<HealthInfo, HealthInfoRegistResponse> toResponse() throws BaseException;

}
