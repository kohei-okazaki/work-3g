package jp.co.ha.common.exception;

/**
 * システム情報の例外クラス<br>
 * <ul>
 * <li>DB関連で発生した例外</li>
 * <li>IO関連で発生した例外</li>
 * <li>Session関連で発生した例外</li>
 * </ul>
 */
public class SystemException extends BaseException {

	public SystemException(Exception e) {
		super(e);
	}

	public SystemException(BaseErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public SystemException(BaseErrorCode errorCode, String detail, Exception e) {
		super(errorCode, detail, e);
	}

}
