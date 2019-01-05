package jp.co.ha.api.service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.common.api.service.BaseService;
import jp.co.ha.common.exception.BaseException;
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
	 * @param request
	 *     リクエスト
	 * @return 健康情報
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfo toEntity(HealthInfoRegistRequest request) throws BaseException;

	/**
	 * 健康情報Entityを健康情報登録レスポンスクラスに変換する
	 *
	 * @param healthInfo
	 *     健康情報
	 * @return 健康情報登録レスポンス
	 * @throws BaseException
	 *     基底例外
	 */
	HealthInfoRegistResponse toResponse(HealthInfo healthInfo) throws BaseException;

}
