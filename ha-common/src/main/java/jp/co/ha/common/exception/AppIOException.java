package jp.co.ha.common.exception;

/**
 * IO系例外クラス
 *
 */
public class AppIOException extends BaseException {

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
	public AppIOException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

}
