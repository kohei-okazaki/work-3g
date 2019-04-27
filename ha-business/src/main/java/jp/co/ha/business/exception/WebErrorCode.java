package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.log.type.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * WEBのエラーコード列挙
 *
 */
public enum WebErrorCode implements BaseErrorCode, BaseEnum {

	/* ERROR */

	/* WARN */
	/** アカウント存在チェックエラー */
	ACCOUNT_ILLEGAL("ACCOUNT_ILLEGAL", "WW_001", LogLevel.WARN),
	/** アカウント不整合エラー */
	ACCOUNT_DELETE("ACCOUNT_DELETE", "WW_002", LogLevel.WARN),
	/** アカウント存在エラー */
	ACCOUNT_EXIST("ACCOUNT_EXIST", "WW_003", LogLevel.WARN),
	/** アカウント不一致エラー */
	ACCOUNT_INVALID_PASSWORD("ACCOUNT_INVALID_PASSWORD", "WW_004", LogLevel.WARN),
	/** アカウント不一致エラー */
	ACCOUNT_EXPIRED("ACCOUNT_EXPIRED", "WW_005", LogLevel.WARN),
	/** リクエスト情報エラー */
	REQUEST_INFO_ERROR("REQUEST_INFO_ERROR", "WW_006", LogLevel.WARN),
	/** 不正リクエストエラー */
	ILLEGAL_ACCESS_ERROR("ILLEGAL_ACCESS_ERROR", "WW_007", LogLevel.WARN),

	;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     value
	 * @param outerErrorCode
	 *     ErrorCode(外部用)
	 * @param logLevel
	 *     ログレベル
	 */
	private WebErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
		this.value = value;
		this.outerErrorCode = outerErrorCode;
		this.logLevel = logLevel;
	}

	/** value */
	private String value;
	/** ErrorCode(外部用) */
	private String outerErrorCode;
	/** ログレベル */
	private LogLevel logLevel;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getOuterErrorCode() {
		return this.outerErrorCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LogLevel getLogLevel() {
		return this.logLevel;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return WebErrorCode
	 */
	public static WebErrorCode of(String value) {
		return BaseEnum.of(WebErrorCode.class, value);
	}
}
