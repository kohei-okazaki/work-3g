package jp.co.ha.common.exception;

/**
 * 予期せぬ例外
 *
 */
public class UnExpectedException extends BaseAppException {

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
	public UnExpectedException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

}
