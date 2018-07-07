package jp.co.ha.business.create;

import java.util.List;

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
	 */
	void create(T entity);

	/**
	 * Entityリストを一括作成する<br>
	 *
	 * @param entityList
	 *     エンティティリスト
	 */
	void create(List<T> entityList);
}
