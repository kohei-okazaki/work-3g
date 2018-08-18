package jp.co.ha.common.exception;

import java.util.stream.Stream;

/**
 * エラーコードの定義<br>
 * API, WEBでのエラーコードをそれぞれ定義する<br>
 */
public enum ErrorCode {

	/** 必須エラー */
	REQUIRE("REQUIRE", "", null, "validate.message.NotEmpty"),
	/** 属性エラー */
	TYPE("TYPE", "", null, "属性エラーです"),
	/** 桁数超過 */
	LENGTH_OVER("LENGTH_OVER", "", null, "validate.message.LengthOver"),
	/** 桁数不足 */
	LENGTH_LACK("LENGTH_LACK", "", null, "validate.message.LengthLack"),

	/** アカウント存在チェックエラー */
	ACCOUNT_ILLEGAL("ACCOUNT_ILLEGAL", "W_001", LogLevel.WARN, "アカウントが存在しません"),
	/** アカウント不整合エラー */
	ACCOUNT_DELETE("ACCOUNT_DELETE", "W_002", LogLevel.WARN, "アカウントが削除済です"),
	/** ファイル処理エラー */
	FILE_WRITE_ERROR("FILE_WRITE_ERROR", "W_003", LogLevel.WARN, "ファイルの処理に失敗しました"),
	/** リクエスト情報エラー */
	REQUEST_INFO_ERROR("REQUEST_INFO_ERROR", "W_004", LogLevel.WARN, "不正リクエストエラーです"),
	/** リクエストID相違エラー */
	REQUEST_ID_INVALID_ERROR("REQUEST_ID_INVALID_ERROR", "W_005", LogLevel.WARN, "リクエストIDが一致しません"),
	/** 不正リクエストエラー */
	ILLEGAL_ACCESS_ERROR("ILLEGAL_ACCESS_ERROR", "W_006", LogLevel.WARN, "不正リクエストエラーです"),
	/** API実行不可エラー */
	API_EXEC_ERROR("API_EXEC_ERROR", "W_007", LogLevel.WARN, "APIの実行に失敗しました"),

	/** アルゴリズムエラー */
	ALGORITH_ERROR("ALGORITH_ERROR", "E_001", LogLevel.ERROR, "アルゴリズムの処理でエラーです"),

	/** DB暗号化・複合化エラー */
	DB_ENCRYPT_ERROR("DB_ENCRYPT_ERROR", "E_002", LogLevel.ERROR, "暗号化・複合化エラー"),
	/** 該当データ存在しないエラー */
	DB_NO_DATA("DB_NO_DATA", "W_008", LogLevel.WARN, "該当のレコードが存在しません"),
	/** DBアクセスエラー */
	DB_ACCESS_ERROR("DB_ACCESS_ERROR", "E_003", LogLevel.ERROR, "DBアクセスに失敗しました"),
	/** SQLの実行に失敗エラー */
	DB_SQL_EXE_ERROR("DB_SQL_EXE_ERROR", "E_004", LogLevel.ERROR, "SQLの実行に失敗しました"),
	/** SQL指定なしエラー */
	DB_SQL_SELECT_ERROR("DB_SQL_SELECT_ERROR", "E_005", LogLevel.ERROR, "SQLが指定されていません"),
	/** DB切断エラー */
	DB_CLOSE_ERROR("DB_CLOSE_ERROR", "E_006", LogLevel.ERROR, "DB切時にエラー"),

	/** ファイルアップロード例外 */
	FILE_UPLOAD_ERROR("FILE_UPLOAD_ERROR", "W_009", LogLevel.WARN, "ファイルアップロード例外"),
	/** ファイル読込例外 */
	FILE_READING_ERROR("FILE_READING_ERROR", "W_010", LogLevel.WARN, "ファイル読み込み例外"),

	/** JSON変換エラー例外 */
	JSON_FORMAT_ERROR("JSON_FORMAT_ERROR", "W_011", LogLevel.WARN, "jsonからbeanへの変換例外"),
	/** JSON変換エラー例外 */
	JSON_MAPPING_ERROR("JSON_MAPPING_ERROR", "W_012", LogLevel.WARN, "jsonからbeanへの変換例外"),
	/** JSON変換エラー例外 */
	JSON_PARSE_ERROR("JSON_PARSE_ERROR", "W_013", LogLevel.WARN, "json形式でありません"),

	/** 実行環境エラー */
	RUNTIME_ERROR("RUNTIME_ERROR", "E_007", LogLevel.ERROR, "実行環境エラー"),

	/** 予期せぬ例外 */
	UNEXPECTED_ERROR("UNEXPECTED_ERROR", "E_008", LogLevel.ERROR, "予期せぬ例外");

	/** 外部用エラーコード */
	private String outerErrorCode;
	/** 内部用エラーコード */
	private String internalErrorCode;
	/** ログレベル */
	private LogLevel logLevel;
	/** エラーメッセージ */
	private String errorMessage;

	/**
	 * コンストラクタ<br>
	 *
	 * @param internalErrorCode
	 *     内部用エラーコード
	 * @param outerErrorCode
	 *     外部用エラーコード
	 * @param logLevel
	 *     ログレベル
	 * @param errorMessage
	 *     エラーメッセージ
	 */
	private ErrorCode(String internalErrorCode, String outerErrorCode, LogLevel logLevel, String errorMessage) {
		this.internalErrorCode = internalErrorCode;
		this.outerErrorCode = outerErrorCode;
		this.logLevel = logLevel;
		this.errorMessage = errorMessage;
	}

	/**
	 * outerErrorCodeを返す<br>
	 *
	 * @return outerErrorCode
	 */
	public String getOuterErrorCode() {
		return outerErrorCode;
	}

	/**
	 * internalErrorCodeを返す<br>
	 *
	 * @return internalErrorCode
	 */
	public String getInternalErrorCode() {
		return internalErrorCode;
	}

	/**
	 * logLevelを返す
	 *
	 * @return logLevel
	 */
	public LogLevel getLogLevel() {
		return this.logLevel;
	}

	/**
	 * errorMessageを返す
	 *
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * 指定されたエラーコードと一致するErrorCodeを返す<br>
	 *
	 * @param errorCode
	 *     エラーコード
	 * @return
	 */
	public static ErrorCode of(String errorCode) {
		return Stream.of(ErrorCode.class.getEnumConstants())
				.filter(code -> code.internalErrorCode.equals(errorCode))
				.findFirst()
				.orElse(null);
	}
}
