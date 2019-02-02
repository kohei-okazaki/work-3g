package jp.co.ha.business.api.service;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.api.service.BaseService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.function.ThrowableFunction;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報登録サービスインターフェース
 *
 */
public interface HealthInfoRegistService
		extends BaseService<HealthInfoRegistRequest, HealthInfoRegistResponse> {

	/**
	 * リクエスト情報を健康情報に変換する
	 *
	 * @return 健康情報
	 * @throws BaseException
	 *     基底例外
	 */
	ThrowableFunction<HealthInfoRegistRequest, HealthInfo> toEntity() throws BaseException;

	/**
	 * 健康情報Entityを健康情報登録レスポンスクラスに変換する
	 *
	 * @return 健康情報登録レスポンス
	 * @throws BaseException
	 *     基底例外
	 */
	ThrowableFunction<HealthInfo, HealthInfoRegistResponse> toResponse() throws BaseException;

}
