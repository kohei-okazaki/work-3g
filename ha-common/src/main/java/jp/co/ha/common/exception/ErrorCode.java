package jp.co.ha.common.exception;

import java.util.stream.Stream;

/**
 * エラーコードの定義<br>
 * API, WEBでのエラーコードをそれぞれ定義する<br>
 */
public enum ErrorCode {

	/** 必須エラー */
	REQUIRE("REQUIRE", "", "必須エラーです"),
	/** 属性エラー */
	TYPE("TYPE", "", "属性エラーです"),
	/** 桁数エラー */
	LENGTH("LENGTH", "", "桁数エラーです"),

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

	/** DB暗号化・複合化エラー */
	DB_ENCRYPT_ERROR("DB_ENCRYPT_ERROR", "ERROR", "暗号化・複合化エラー"),
	/** 該当データ存在しないエラー */
	DB_NO_DATA("DB_NO_DATA", "ERROR", "該当のレコードが存在しません"),
	/** DBアクセスエラー */
	DB_ACCESS_ERROR("DB_ACCESS_ERROR", "ERROR", "DBアクセスに失敗しました"),

	/** ファイルアップロード例外 */
	FILE_UPLOAD_ERROR("FILE_UPLOAD_ERROR", "WARN", "ファイルアップロード例外"),
	/** ファイル読込例外 */
	FILE_READING_ERROR("FILE_READING_ERROR", "WARN", "ファイル読み込み例外"),

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
