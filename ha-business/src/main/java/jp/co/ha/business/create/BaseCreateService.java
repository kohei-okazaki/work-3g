package jp.co.ha.business.create;

import jp.co.ha.common.dao.BaseDao;

/**
 * 基底Entity作成サービスインターフェース<br>
 *
 * @param <T>
 */
public interface BaseCreateService<T, D extends BaseDao> {

	/**
	 * Entityを作成する<br>
	 * @param entity
	 */
	void create(T entity);
}
