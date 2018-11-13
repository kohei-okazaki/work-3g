package jp.co.ha.common.exception;

import java.util.stream.Stream;

import jp.co.ha.common.log.type.LogLevel;

/**
 * エラーコードの定義<br>
 * API, WEBでのエラーコードをそれぞれ定義する<br>
 */
public enum ErrorCode {

	/** 必須エラー */
	REQUIRE("REQUIRE", "", null, "", "validate.message.NotEmpty"),
	/** 桁数超過 */
	LENGTH_OVER("LENGTH_OVER", "", null, "", "validate.message.LengthOver"),
	/** 桁数不足 */
	LENGTH_LACK("LENGTH_LACK", "", null, "", "validate.message.LengthLack"),
	/** 型不正 */
	TYPE_VALID("TYPE_VALID", "", null, "", "validate.message.TypeError"),

	/** アカウント存在チェックエラー */
	ACCOUNT_ILLEGAL("ACCOUNT_ILLEGAL", "W_001", LogLevel.WARN, "アカウントが存在しません", "error.account.illegal"),
	/** アカウント不整合エラー */
	ACCOUNT_DELETE("ACCOUNT_DELETE", "W_002", LogLevel.WARN, "アカウントが削除済です", "error.account.delete"),
	/** ファイル処理エラー */
	FILE_WRITE_ERROR("FILE_WRITE_ERROR", "W_003", LogLevel.WARN, "ファイルの処理に失敗しました", "error.file.write"),
	/** リクエスト情報エラー */
	REQUEST_INFO_ERROR("REQUEST_INFO_ERROR", "W_004", LogLevel.WARN, "リクエスト情報エラーです", "error.request.info"),
	/** リクエストID相違エラー */
	REQUEST_ID_INVALID_ERROR("REQUEST_ID_INVALID_ERROR", "W_005", LogLevel.WARN, "リクエストIDが一致しません", "error.request.id.invalid"),
	/** 不正リクエストエラー */
	ILLEGAL_ACCESS_ERROR("ILLEGAL_ACCESS_ERROR", "W_006", LogLevel.WARN, "不正リクエストエラーです", "error.illegal.access"),
	/** API実行不可エラー */
	API_EXEC_ERROR("API_EXEC_ERROR", "W_007", LogLevel.WARN, "APIの実行に失敗しました", "error.api.exec"),

	/** アルゴリズムエラー */
	ALGORITH_ERROR("ALGORITH_ERROR", "E_001", LogLevel.ERROR, "アルゴリズムの処理でエラーです", "error.algorithym"),

	/** DB暗号化・複合化エラー */
	DB_ENCRYPT_ERROR("DB_ENCRYPT_ERROR", "E_002", LogLevel.ERROR, "暗号化・複合化エラー", "error.db.encrypt"),
	/** 該当データ存在しないエラー */
	DB_NO_DATA("DB_NO_DATA", "W_008", LogLevel.WARN, "該当のレコードが存在しません", "error.db.non.data"),
	/** DBアクセスエラー */
	DB_ACCESS_ERROR("DB_ACCESS_ERROR", "E_003", LogLevel.ERROR, "DBアクセスに失敗しました", "error.db.access"),
	/** SQLの実行に失敗エラー */
	DB_SQL_EXE_ERROR("DB_SQL_EXE_ERROR", "E_004", LogLevel.ERROR, "SQLの実行に失敗しました", "error.db.sql.exe"),
	/** SQL指定なしエラー */
	DB_SQL_SELECT_ERROR("DB_SQL_SELECT_ERROR", "E_005", LogLevel.ERROR, "SQLが指定されていません", "error.db.sql.select"),
	/** DB切断エラー */
	DB_CLOSE_ERROR("DB_CLOSE_ERROR", "E_006", LogLevel.ERROR, "DB切時にエラー", "error.db.close"),

	/** ファイルアップロード例外 */
	FILE_UPLOAD_ERROR("FILE_UPLOAD_ERROR", "W_009", LogLevel.WARN, "ファイルアップロード例外", "error.file.upload"),
	/** ファイル読込例外 */
	FILE_READING_ERROR("FILE_READING_ERROR", "W_010", LogLevel.WARN, "ファイル読み込み例外", "error.file.reading"),

	/** JSON変換エラー例外 */
	JSON_FORMAT_ERROR("JSON_FORMAT_ERROR", "W_011", LogLevel.WARN, "jsonからbeanへの変換例外", "error.json.format"),
	/** JSON変換エラー例外 */
	JSON_MAPPING_ERROR("JSON_MAPPING_ERROR", "W_012", LogLevel.WARN, "jsonからbeanへの変換例外", "error.json.mapping"),
	/** JSON変換エラー例外 */
	JSON_PARSE_ERROR("JSON_PARSE_ERROR", "W_013", LogLevel.WARN, "json形式でありません", "error.json.parse"),

	/** 実行環境エラー */
	RUNTIME_ERROR("RUNTIME_ERROR", "E_007", LogLevel.ERROR, "実行環境エラー", "error.runtime"),

	/** 予期せぬ例外 */
	UNEXPECTED_ERROR("UNEXPECTED_ERROR", "E_008", LogLevel.ERROR, "予期せぬ例外が発生しました", "error.unexpected"),

	/** 健康情報登録API必須エラー */
	HEALTH_INFO_REG_EMPTY("HEALTH_INFO_REG_EMPTY", "W_014", LogLevel.WARN, "健康情報登録API必須エラー", "error.health.info.reg.empty");

	/** 内部用エラーコード */
	private String internalErrorCode;
	/** 外部用エラーコード */
	private String outerErrorCode;
	/** ログレベル */
	private LogLevel logLevel;
	/** エラーメッセージ */
	private String errorMessage;
	/** validateで使うメッセージ */
	private String validateMessage;

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
	 * @param validateMessage
	 *     validateMessage
	 */
	private ErrorCode(String internalErrorCode, String outerErrorCode, LogLevel logLevel, String errorMessage, String validateMessage) {
		this.internalErrorCode = internalErrorCode;
		this.outerErrorCode = outerErrorCode;
		this.logLevel = logLevel;
		this.errorMessage = errorMessage;
		this.validateMessage = validateMessage;
	}

	/**
	 * outerErrorCodeを返す<br>
	 *
	 * @return outerErrorCode
	 */
	public String getOuterErrorCode() {
		return this.outerErrorCode;
	}

	/**
	 * internalErrorCodeを返す<br>
	 *
	 * @return internalErrorCode
	 */
	public String getInternalErrorCode() {
		return this.internalErrorCode;
	}

	/**
	 * logLevelを返す<br>
	 *
	 * @return logLevel
	 */
	public LogLevel getLogLevel() {
		return this.logLevel;
	}

	/**
	 * errorMessageを返す<br>
	 *
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * validateMessageを返す<br>
	 *
	 * @return validateMessage
	 */
	public String getValidateMessage() {
		return this.validateMessage;
	}

	/**
	 * 指定された内部エラーコードと一致するErrorCodeを返す<br>
	 *
	 * @param innerErrorCode
	 *    内部エラーコード
	 * @return
	 */
	public static ErrorCode of(String innerErrorCode) {
		return Stream.of(ErrorCode.class.getEnumConstants())
				.filter(code -> code.internalErrorCode.equals(innerErrorCode))
				.findFirst()
				.orElse(null);
	}
}
