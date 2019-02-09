package jp.co.ha.common.exception;

/**
 * 予期せぬ例外クラス
 *
 */
public class UnExpectedException extends BaseException {

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
	public UnExpectedException(BaseErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

}
