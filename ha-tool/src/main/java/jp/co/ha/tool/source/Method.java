package jp.co.ha.tool.source;

import jp.co.ha.tool.type.AccessType;

public abstract class Method {

	/** 当メソッドのフィールド情報 */
	protected Field field;
	/** メソッドのアクセスタイプ */
	protected AccessType accessType;

	/**
	 * コンストラクタ<br>
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
	 * メソッド名を返す<br>
	 *
	 * @return
	 */
	protected abstract String getMethodName();

}
