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

	/** 値 */
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 */
	private LogLevel(String value) {
		this.value = value;
	}

	/**
	 * 値を返す
	 *
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * 指定した<code>logLevel</code>と自身が一致するかどうか返す
	 *
	 * @param logLevel
	 *     LogLevel
	 * @return 判定結果
	 */
	public boolean is(LogLevel logLevel) {
		return this == logLevel;
	}

}
