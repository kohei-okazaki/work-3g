package jp.co.ha.business.db.crud.delete;

import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報削除サービスインターフェース
 * 
 * @since 1.0
 */
public interface HealthInfoDeleteService {

	/**
	 * 指定された健康情報IDの健康情報を削除する
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 * @throws BaseException
	 *     基底例外
	 */
	void deleteByUserId(Integer healthInfoId) throws BaseException;

}
