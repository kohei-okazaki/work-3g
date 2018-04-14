package jp.co.ha.common.exception;

/**
 * IO系例外クラス<br>
 *
 */
public class AppIOException extends BaseAppRuntimeException {

	/**
	 * コンストラクタ<br>
	 * @param errorCode
	 * @param errorMessage
	 */
	public AppIOException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
