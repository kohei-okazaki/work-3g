package jp.co.ha.common.type;

/**
 * フラグ値列挙
 *
 */
public enum CommonFlag implements BaseEnum {

	/** true */
	TRUE("1"),
	/** false */
	FALSE("0");

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 */
	private CommonFlag(String value) {
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
	 * {@inheritDoc}
	 */
	@Override
	public boolean is(String value) {
		return this.value.equals(value);
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return CommonFlag
	 */
	public static CommonFlag of(String value) {
		return BaseEnum.of(CommonFlag.class, value);
	}
}
