package jp.co.ha.business.update;

import jp.co.ha.common.exception.BaseAppException;

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
	 * @throws BaseAppException
	 */
	void update(T entity) throws BaseAppException;
}
