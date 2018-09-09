package jp.co.ha.business.db.crud.update;

import jp.co.ha.common.exception.BaseException;

/**
 * 基底Entity更新サービスインターフェース<br>
 *
 * @param <T>
 *     Entityクラス
 */
public interface BaseUpdateService<T> {

	/**
	 * Entityを更新する<br>
	 *
	 * @param entity
	 *     Entity
	 * @throws BaseException
	 */
	void update(T entity) throws BaseException;
}
