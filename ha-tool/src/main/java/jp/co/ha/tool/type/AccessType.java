package jp.co.ha.tool.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * アクセス列挙
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

	private AccessType(String value) {
		this.value = value;
	}

	private String value;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}
}
