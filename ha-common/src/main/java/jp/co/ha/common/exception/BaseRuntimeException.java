package jp.co.ha.common.exception;

/**
 * アプリないで扱う基底Runtime例外クラス
 *
 * @since 1.0
 */
public abstract class BaseRuntimeException extends RuntimeException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = -5362739275313802447L;

	/** エラーコード */
	private BaseErrorCode errorCode;
	/** 詳細 */
	private String detail;

	/**
	 * コンストラクタ
	 *
	 * @param e
	 *     例外クラス
	 */
	public BaseRuntimeException(Exception e) {
		super(e);
	}

	/**
	 * コンストラクタ
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 */
	public BaseRuntimeException(BaseErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
	}

	/**
	 * コンストラクタ
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 * @param e
	 *     例外クラス
	 */
	public BaseRuntimeException(BaseErrorCode errorCode, String detail, Exception e) {
		super(e);
		this.errorCode = errorCode;
		this.detail = detail;
	}

	/**
	 * errorCodeを返す
	 *
	 * @return errorCode エラーコード
	 */
	public BaseErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * detailを返す
	 *
	 * @return detail 詳細
	 */
	public String getDetail() {
		return detail;
	}

}
