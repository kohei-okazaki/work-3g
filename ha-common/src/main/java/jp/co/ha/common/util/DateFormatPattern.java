package jp.co.ha.common.util;

/**
 * Dateフォーマット
 *
 */
public enum DateFormatPattern {

	YYYYMMDD("yyyy/MM/dd"),
	YYYYMMDD_HHMMSS("yyyy/MM/dd HH:mm:ss");

	/** フォーマット名 */
	private String value;

	/**
	 * コンストラクタ<br>
	 *
	 * @param value
	 *            フォーマット名
	 */
	private DateFormatPattern(String value) {
		this.value = value;
	}

	/**
	 * フォーマット名を返す<br>
	 *
	 * @return フォーマット名
	 */
	public String getValue() {
		return this.value;
	}

}
