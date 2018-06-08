package jp.co.ha.business.create;

import java.util.List;

import jp.co.ha.common.dao.BaseDao;

/**
 * 基底Entity作成サービスインターフェース<br>
 *
 * @param <T>
 *            Entity
 * @param <D>
 *            Daoクラス
 */
public interface BaseCreateService<T, D extends BaseDao> {

	/**
	 * Entityを作成する<br>
	 *
	 * @param entity
	 *            Entity
	 */
	void create(T entity);

	/**
	 * Entityリストを一括作成する<br>
	 *
	 * @param entityList
	 *            エンティティリスト
	 */
	void create(List<T> entityList);
}
