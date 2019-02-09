package jp.co.ha.common.exception;

/**
 * API例外クラス
 *
 */
public class ApiException extends BaseException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

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

}
