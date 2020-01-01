package jp.co.ha.common.exception;

/**
 * アプリ内で扱う基底例外クラス
 *
 * @since 1.0
 */
public abstract class BaseException extends Exception {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = -5399990606605542371L;

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
	public BaseException(Exception e) {
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
	public BaseException(BaseErrorCode errorCode, String detail) {
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
	public BaseException(BaseErrorCode errorCode, String detail, Exception e) {
		super(e);
		this.errorCode = errorCode;
		this.detail = detail;
	}

	/**
	 * {@linkplain BaseErrorCode}を返す
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
