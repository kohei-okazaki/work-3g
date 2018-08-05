package jp.co.ha.common.util;

/**
 * Dateフォーマット
 *
 */
public enum DateFormatPattern {

	YYYYMMDD("yyyy/MM/dd"),
	YYYYMMDD_HHMMSS("yyyy/MM/dd HH:mm:ss");

	private String value;

	private DateFormatPattern(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
