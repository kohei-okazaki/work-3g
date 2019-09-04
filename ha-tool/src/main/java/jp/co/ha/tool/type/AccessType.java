package jp.co.ha.tool.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * アクセス修飾子の列挙
 *
 */
public enum AccessType implements BaseEnum {

	/** public */
	PUBLIC("public"),
	/** protected */
	PROTECTED("protected"),
	/** デフォルト */
	DEFAULT(""),
	/** private */
	PRIVATE("private");

	/**
	 * コンストラクタ
	 * 
	 * @param value
	 *     値
	 */
	private AccessType(String value) {
		this.value = value;
	}

	/** 値 */
	private String value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return AccessType
	 */
	public static AccessType of(String value) {
		return BaseEnum.of(AccessType.class, value);
	}
}
