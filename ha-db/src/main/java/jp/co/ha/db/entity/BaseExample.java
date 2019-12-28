package jp.co.ha.db.entity;

/**
 * MyBatisのExampleクラスの基底Exampleクラス<br>
 * 検索上限数等すべてのTableに対し、共通で処理すべきものを定義する
 *
 * @since 1.0
 */
public abstract class BaseExample {

	/** 検索上限数 */
	protected Integer limit;

	/**
	 * limitを返す
	 *
	 * @return limit
	 */
	public Integer getLimit() {
		return limit;
	}

	/**
	 * limitを設定する
	 *
	 * @param limit
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
