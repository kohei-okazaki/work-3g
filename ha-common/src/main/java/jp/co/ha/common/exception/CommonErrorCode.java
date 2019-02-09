package jp.co.ha.common.exception;

import jp.co.ha.common.log.type.LogLevel;
import jp.co.ha.common.type.BaseEnum;

/**
 * 共通エラーコード
 *
 */
public enum CommonErrorCode implements BaseErrorCode, BaseEnum {

	/* ERROR */
	/** 実行環境エラー */
	RUNTIME_ERROR("RUNTIME_ERROR", "CE_001", LogLevel.ERROR),
	/** 予期せぬ例外 */
	UNEXPECTED_ERROR("UNEXPECTED_ERROR", "CE_002", LogLevel.ERROR),
	/** アルゴリズムエラー */
	ALGORITH_ERROR("ALGORITH_ERROR", "CE_003", LogLevel.ERROR),
	/** DB暗号化・複合化エラー */
	DB_ENCRYPT_ERROR("DB_ENCRYPT_ERROR", "CE_004", LogLevel.ERROR),
	/** DBアクセスエラー */
	DB_ACCESS_ERROR("DB_ACCESS_ERROR", "CE_005", LogLevel.ERROR),
	/** SQLの実行に失敗エラー */
	DB_SQL_EXE_ERROR("DB_SQL_EXE_ERROR", "CE_006", LogLevel.ERROR),
	/** SQL指定なしエラー */
	DB_SQL_SELECT_ERROR("DB_SQL_SELECT_ERROR", "CE_007", LogLevel.ERROR),
	/** DB切断エラー */
	DB_CLOSE_ERROR("DB_CLOSE_ERROR", "CE_008", LogLevel.ERROR),


	/* WARN */
	/** 該当データ存在しないエラー */
	DB_NO_DATA("DB_NO_DATA", "CW_001", LogLevel.WARN),
	/** JSON変換エラー例外 */
	JSON_FORMAT_ERROR("JSON_FORMAT_ERROR", "CW_002", LogLevel.WARN),
	/** JSON変換エラー例外 */
	JSON_MAPPING_ERROR("JSON_MAPPING_ERROR", "CW_003", LogLevel.WARN),
	/** JSON変換エラー例外 */
	JSON_PARSE_ERROR("JSON_PARSE_ERROR", "CW_004", LogLevel.WARN),
	/** ファイルアップロード例外 */
	FILE_UPLOAD_ERROR("FILE_UPLOAD_ERROR", "CW_005", LogLevel.WARN),
	/** ファイル読込例外 */
	FILE_READING_ERROR("FILE_READING_ERROR", "CW_006", LogLevel.WARN),
	/** ファイル処理エラー */
	FILE_WRITE_ERROR("FILE_WRITE_ERROR", "CW_007", LogLevel.WARN),
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
	private CommonErrorCode(String value, String outerErrorCode, LogLevel logLevel) {
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
