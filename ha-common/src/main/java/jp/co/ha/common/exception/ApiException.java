package jp.co.ha.common.exception;

/**
 * API例外クラス
 * 
 * @since 1.0
 */
public class ApiException extends BaseException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 *
	 * @param e
	 *     例外クラス
	 */
	public ApiException(Exception e) {
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
	public ApiException(BaseErrorCode errorCode, String detail) {
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
	public ApiException(BaseErrorCode errorCode, String detail, Exception e) {
		super(errorCode, detail, e);
	}

}
