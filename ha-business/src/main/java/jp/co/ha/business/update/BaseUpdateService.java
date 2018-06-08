package jp.co.ha.business.update;

import jp.co.ha.common.dao.BaseDao;

/**
 * 基底Entity更新サービスインターフェース<br>
 *
 * @param <T>
 */
public interface BaseUpdateService<T, D extends BaseDao> {

	/**
	 * Entityを更新する<br>
	 * @param entity
	 */
	void update(T entity);
}
