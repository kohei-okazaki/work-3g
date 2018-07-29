package jp.co.ha.common.util;

import java.util.regex.Pattern;

/**
 * 正規表現列挙<br>
 *
 */
public enum RegixPattern {

	/** 半角数字 */
	HALF_NUMBER("^[0-9]*$"),
	/** 半角数字とピリオド */
	HALF_NUMBER_PERIOD("^[0-9.]*$"),
	/** 半角英数字 */
	HALF_CHAR("^[0-9a-zA-Z]*$"),
	/** URL */
	URL("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");

	/** 正規表現 */
	private String pattern;

	/**
	 * コンストラクタ<br>
	 *
	 * @param pattern
	 *     正規表現
	 */
	private RegixPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * 正規表現を返す<br>
	 *
	 * @return
	 */
	public String getPattern() {
		return this.pattern;
	}

	/**
	 * 指定した文字列(target)が指定した正規表現(patter)かどうか判定する<br>
	 * 正しい場合true, 異なる場合falseを返す<br>
	 *
	 * @param target
	 *     対象文字列
	 * @param pattern
	 *     正規表現
	 * @return
	 */
	public static boolean isPattern(String target, RegixPattern pattern) {
		return Pattern.compile(pattern.getPattern()).matcher(target).find();
	}

}
