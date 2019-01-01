package jp.co.ha.business.db.crud.create;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報作成サービスインターフェース
 *
 */
public interface HealthInfoCreateService {

	/**
	 * 指定した健康情報を登録する
	 *
	 * @param entity
	 *     健康情報
	 * @throws BaseException
	 *     基底例外
	 */
	void create(HealthInfo entity) throws BaseException;
}
