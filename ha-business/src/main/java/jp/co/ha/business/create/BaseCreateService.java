package jp.co.ha.business.create;

import jp.co.ha.common.exception.BaseAppException;

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
	 * @throws BaseAppException
	 */
	void create(T entity) throws BaseAppException;

}
