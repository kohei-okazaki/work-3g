package jp.co.ha.common.util;

public enum DateFormatDefine {

	YYYYMMDD("yyyy/MM/dd"),
	YYYYMMDD_HHMMSS("yyyy/MM/dd HH:mm:ss");

	private String value;

	private DateFormatDefine(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
