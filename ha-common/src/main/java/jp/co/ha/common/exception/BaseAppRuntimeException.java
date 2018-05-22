package jp.co.ha.common.exception;

/**
 * 基底Runtime例外クラス<br>
 *
 */
public abstract class BaseAppRuntimeException extends RuntimeException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/** エラーコード */
	private ErrorCode errorCode;
	/** エラーメッセージ */
	protected String errorMessage;

	/**
	 * コンストラクタ<br>
	 *
	 * @param errorCode
	 *            エラーコード
	 * @param detail
	 *            詳細
	 */
	public BaseAppRuntimeException(ErrorCode errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * errorCodeを返す
	 *
	 * @return errorCode エラーコード
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * errorMessageを返す
	 *
	 * @return errorMessage エラーメッセージ
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

}
