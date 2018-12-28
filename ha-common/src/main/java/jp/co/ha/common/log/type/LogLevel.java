package jp.co.ha.common.log.type;

import jp.co.ha.common.type.BaseEnum;

/**
 * ログレベルの列挙<br>
 *
 */
public enum LogLevel implements BaseEnum {

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
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value 値
	 * @return LogLevel
	 */
	public static LogLevel of(String value) {
		return BaseEnum.of(LogLevel.class, value);
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
