package jp.co.ha.tool.source;

import jp.co.ha.tool.type.AccessType;

/**
 * Method
 *
 * @param <T> 任意の型
 */
public abstract class Method<T> {

	/** 当メソッドのフィールド情報 */
	protected Field<T> field;
	/** メソッドのアクセスタイプ */
	protected AccessType accessType;

	/**
	 * コンストラクタ
	 *
	 * @param field
	 *     当メソッドのフィールド情報
	 * @param accessType
	 *     メソッドのアクセスタイプ
	 */
	public Method(Field<T> field, AccessType accessType) {
		this.field = field;
		this.accessType = accessType;
	}

	/**
	 * メソッド名を返す
	 *
	 * @return メソッド名
	 */
	protected abstract String getMethodName();

}
