package jp.co.ha.common.type;

/**
 * Dateフォーマット
 *
 */
public enum DateFormatType {

	YYYYMMDD("yyyy/MM/dd"),
	YYYYMMDD_HHMMSS("yyyy/MM/dd HH:mm:ss");

	private String value;

	private DateFormatType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
