package jp.co.ha.business.update;

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
	 */
	void update(T entity);
}
