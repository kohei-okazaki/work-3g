package jp.co.ha.common.exception;

/**
 * セッション不正例外クラス
 *
 */
public class SessionIllegalException extends BaseException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 *
	 * @param e
	 *     例外クラス
	 */
	public SessionIllegalException(Exception e) {
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
	public SessionIllegalException(BaseErrorCode errorCode, String detail) {
		super(errorCode, detail);
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
	public SessionIllegalException(BaseErrorCode errorCode, String detail, Exception e) {
		super(errorCode, detail, e);
	}
}
