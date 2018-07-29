package jp.co.ha.common.exception;

import java.util.stream.Stream;

/**
 * エラーコードの定義<br>
 * API, WEBでのエラーコードをそれぞれ定義する<br>
 */
public enum ErrorCode {

	/** 必須エラー */
	REQUIRE("REQUIRE", "", "validate.message.NotEmpty"),
	/** 属性エラー */
	TYPE("TYPE", "", "属性エラーです"),
	/** 桁数超過 */
	LENGTH_OVER("LENGTH_OVER", "", "validate.message.LengthOver"),
	/** 桁数不足 */
	LENGTH_LACK("LENGTH_LACK", "", "validate.message.LengthLack"),

	/** アカウント存在チェックエラー */
	ACCOUNT_ILLEGAL("ACCOUNT_ILLEGAL", "WARN", "アカウントが存在しません"),
	/** アカウント不整合エラー */
	ACCOUNT_DELETE("ACCOUNT_DELETE", "WARN", "アカウントが削除済です"),
	/** ファイル処理エラー */
	FILE_WRITE_ERROR("FILE_WRITE_ERROR", "ERROR", "ファイルの処理に失敗しました"),
	/** リクエスト情報エラー */
	REQUEST_INFO_ERROR("REQUEST_INFO_ERROR", "ERROR", "不正リクエストエラーです"),
	/** リクエストID相違エラー */
	REQUEST_ID_INVALID_ERROR("REQUEST_ID_INVALID_ERROR", "ERROR", "リクエストIDが一致しません"),
	/** 不正リクエストエラー */
	ILLEGAL_ACCESS_ERROR("ILLEGAL_ACCESS_ERROR", "WARN", "不正リクエストエラーです"),
	/** API実行不可エラー */
	API_EXEC_ERROR("API_EXEC_ERROR", "WARN", "APIの実行に失敗しました"),

	/** アルゴリズムエラー */
	ALGORITH_ERROR("ALGORITH_ERROR", "ERROR", "アルゴリズムの処理でエラーです"),

	/** DB暗号化・複合化エラー */
	DB_ENCRYPT_ERROR("DB_ENCRYPT_ERROR", "ERROR", "暗号化・複合化エラー"),
	/** 該当データ存在しないエラー */
	DB_NO_DATA("DB_NO_DATA", "ERROR", "該当のレコードが存在しません"),
	/** DBアクセスエラー */
	DB_ACCESS_ERROR("DB_ACCESS_ERROR", "ERROR", "DBアクセスに失敗しました"),
	/** SQLの実行に失敗エラー */
	DB_SQL_EXE_ERROR("DB_SQL_EXE_ERROR", "ERROR", "SQLの実行に失敗しました"),
	/** SQL指定なしエラー */
	DB_SQL_SELECT_ERROR("DB_SQL_SELECT_ERROR", "ERROR", "SQLが指定されていません"),
	/** DB切断エラー */
	DB_CLOSE_ERROR("DB_CLOSE_ERROR", "ERROR", "DB切時にエラー"),

	/** ファイルアップロード例外 */
	FILE_UPLOAD_ERROR("FILE_UPLOAD_ERROR", "WARN", "ファイルアップロード例外"),
	/** ファイル読込例外 */
	FILE_READING_ERROR("FILE_READING_ERROR", "WARN", "ファイル読み込み例外"),

	/** JSON変換エラー例外 */
	JSON_FORMAT_ERROR("JSON_FORMAT_ERROR", "WARN", "jsonからbeanへの変換例外"),
	/** JSON変換エラー例外 */
	JSON_MAPPING_ERROR("JSON_MAPPING_ERROR", "WARN", "jsonからbeanへの変換例外"),
	/** JSON変換エラー例外 */
	JSON_PARSE_ERROR("JSON_PARSE_ERROR", "WARN", "json形式でありません"),

	/** 実行環境エラー */
	RUNTIME_ERROR("RUNTIME_ERROR", "ERROR", "実行環境エラー"),

	/** 予期せぬ例外 */
	UNEXPECTED_ERROR("UNEXPECTED_ERROR", "ERROR", "予期せぬ例外");

	/** エラーコード */
	private String errorCode;
	/** ログレベル */
	private String logLevel;
	/** エラーメッセージ */
	private String errorMessage;

	/**
	 * コンストラクタ<br>
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param logLevel
	 *     ログレベル
	 * @param errorMessage
	 *     エラーメッセージ
	 */
	private ErrorCode(String errorCode, String logLevel, String errorMessage) {
		this.errorCode = errorCode;
		this.logLevel = logLevel;
		this.errorMessage = errorMessage;
	}

	/**
	 * errorCodeを返す
	 *
	 * @return errorCode
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * logLevelを返す
	 *
	 * @return logLevel
	 */
	public String getLogLevel() {
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
				.filter(code -> code.errorCode.equals(errorCode))
				.findFirst()
				.orElse(null);
	}
}
