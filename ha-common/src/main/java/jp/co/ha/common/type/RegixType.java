package jp.co.ha.common.type;

/**
 * 正規表現列挙<br>
 *
 */
public enum RegixType {

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
	private String pattern;

	/**
	 * コンストラクタ<br>
	 *
	 * @param pattern
	 *     正規表現
	 */
	private RegixType(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * 正規表現を返す<br>
	 *
	 * @return 正規表現
	 */
	public String getPattern() {
		return this.pattern;
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
		return target.matches(this.getPattern());
	}

}
