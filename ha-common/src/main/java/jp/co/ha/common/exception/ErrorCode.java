package jp.co.ha.common.exception;

/**
 * エラーコードの定義<br>
 * API, MVCでのエラーコードをそれぞれ定義する<br>
 */
public enum ErrorCode {

	/** 必須エラー */
	REQUIRE("REQUIRE", "", "必須エラーです"),
	/** 属性エラー*/
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
	REQUEST_INFO_ERROR("REQUEST_INFO_ERROR", "ERROR", "不正リクエストエラーです");


	/** エラーコード */
	private String errorCode;
	/** ログレベル */
	private String logLevel;
	/** エラーメッセージ */
	private String errorMessage;

	/**
	 * コンストラクタ<br>
	 * @param errorCode
	 * @param logLevel
	 * @param errorMessage
	 */
	private ErrorCode(String errorCode, String logLevel, String errorMessage) {
		this.errorCode = errorCode;
		this.logLevel = logLevel;
		this.errorMessage = errorMessage;
	}

	/**
	 * errorCodeを返す
	 * @return errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * logLevelを返す
	 * @return logLevel
	 */
	public String getLogLevel() {
		return logLevel;
	}

	/**
	 * errorMessageを返す
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 指定されたエラーコードと一致するErrorCodeを返す<br>
	 * @param errorCode
	 * @return
	 */
	public static ErrorCode of(String errorCode) {

		for (ErrorCode code : ErrorCode.class.getEnumConstants()) {
			if (code.errorCode.equals(errorCode)) {
				return code;
			}
		}
		return null;
	}

}
