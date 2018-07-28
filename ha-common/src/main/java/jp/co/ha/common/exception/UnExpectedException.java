package jp.co.ha.common.exception;

/**
 * 予期せぬ例外
 *
 */
public class UnExpectedException extends BaseAppException {

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
