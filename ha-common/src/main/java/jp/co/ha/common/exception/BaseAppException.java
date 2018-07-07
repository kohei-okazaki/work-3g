package jp.co.ha.common.exception;

/**
 * アプリ内で扱う基底例外クラス<br>
 *
 */
public abstract class BaseAppException extends Exception {

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
	public BaseAppException(ErrorCode errorCode, String detail) {
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
