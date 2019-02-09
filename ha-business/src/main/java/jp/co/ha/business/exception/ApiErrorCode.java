package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.log.type.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * APIのエラーコード列挙
 *
 */
public enum ApiErrorCode implements BaseErrorCode, BaseEnum {

	/* ERROR */
	API_EXEC_ERROR("API_EXEC_ERROR", "AE_001", LogLevel.WARN),

	/* WARN */
	/** 健康情報登録API必須エラー */
	HEALTH_INFO_REG_EMPTY("HEALTH_INFO_REG_EMPTY", "AW_001", LogLevel.WARN),
	/** 健康情報照会API必須エラー */
	HEALTH_INFO_REF_EMPTY("HEALTH_INFO_REG_EMPTY", "AW_002", LogLevel.WARN),
	/** リクエスト種別エラー */
	REQUEST_TYPE_INVALID_ERROR("REQUEST_TYPE_INVALID_ERROR", "AW_003", LogLevel.WARN),

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
	private ApiErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
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
}
