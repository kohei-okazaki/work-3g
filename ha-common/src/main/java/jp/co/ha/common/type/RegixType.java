package jp.co.ha.common.type;

/**
 * 正規表現列挙<br>
 *
 */
public enum RegixType implements BaseEnum {

	/** 半角数字 */
	HALF_NUMBER("^[0-9]*$"),
	/** 半角数字とピリオド */
	HALF_NUMBER_PERIOD("^[0-9.]*$"),
	/** 半角英数字 */
	HALF_CHAR("^[0-9a-zA-Z]*$"),
	/** URL */
	URL("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"),
	/** メールアドレス */
	MAIL_ADDRESS("[A-Za-z0-9._+]+@[A-Za-z]+.[A-Za-z]");

	/** 正規表現 */
	private String value;

	/**
	 * コンストラクタ<br>
	 *
	 * @param value
	 *     正規表現
	 */
	private RegixType(String value) {
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
	 * 指定した文字列<code>target</code>が自身の正規表現と一致するかどうか判定する<br>
	 * 正しい場合true, 異なる場合falseを返す<br>
	 *
	 * @param target
	 *     対象文字列
	 * @return 判定結果
	 */
	public boolean is(String target) {
		return target.matches(this.getValue());
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value 値
	 * @return Charset
	 */
	public static RegixType of(String value) {
		return BaseEnum.of(RegixType.class, value);
	}

}
