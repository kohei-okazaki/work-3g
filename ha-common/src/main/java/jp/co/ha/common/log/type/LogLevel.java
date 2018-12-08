package jp.co.ha.common.log.type;

/**
 * ログレベルの列挙<br>
 *
 */
public enum LogLevel {

	/** DEBUG */
	DEBUG("DEBUG"),
	/** INFO */
	INFO("INFO"),
	/** WARN */
	WARN("WARN"),
	/** ERROR */
	ERROR("ERROR"),
	/** FATAL */
	FATAL("FATAL");

	private String value;

	private LogLevel(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public boolean is(LogLevel logLevel) {
		return this == logLevel;
	}

}
