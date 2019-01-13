package jp.co.ha.tool.source;

import jp.co.ha.tool.type.AccessType;

/**
 * Method
 *
 */
public abstract class Method {

	/** 当メソッドのフィールド情報 */
	protected Field field;
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
	public Method(Field field, AccessType accessType) {
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
