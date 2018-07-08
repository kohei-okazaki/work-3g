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
	/** 詳細 */
	private String detail;

	/**
	 * コンストラクタ<br>
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 */
	public BaseAppRuntimeException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
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
	 * detailを返す<br>
	 *
	 * @return detail 詳細
	 */
	public String getDetail() {
		return detail;
	}

}
