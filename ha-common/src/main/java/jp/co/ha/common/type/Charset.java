package jp.co.ha.common.type;

/**
 * charset列挙<br>
 *
 */
public enum Charset implements BaseEnum {

	/** MS932 */
	MS_932("MS932"),
	/** UTF-8 */
	UTF_8("UTF-8");

	/** 名前 */
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     value
	 */
	private Charset(String value) {
		this.value = value;
	}

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
	@SuppressWarnings("unchecked")
	public Charset of(String value) {
		return BaseEnum.of(this.getClass(), value);
	}
}
