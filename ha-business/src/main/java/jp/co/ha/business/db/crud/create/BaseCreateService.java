package jp.co.ha.business.db.crud.create;

import jp.co.ha.common.exception.BaseException;

/**
 * 基底Entity作成サービスインターフェース<br>
 *
 * @param <T>
 *     Entity
 */
public interface BaseCreateService<T> {

	/**
	 * Entityを作成する<br>
	 *
	 * @param entity
	 *     Entity
	 * @throws BaseException
	 */
	void create(T entity) throws BaseException;

}
