package jp.co.ha.common.exception;

/**
 * IO系例外クラス<br>
 *
 */
public class AppIOException extends BaseAppRuntimeException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ<br>
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 */
	public AppIOException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
