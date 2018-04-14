package jp.co.ha.common.exception;

public class AccountCreateException extends BaseAppException {

	/**
	 * コンストラクタ<br>
	 * @param errorCode
	 * @param detail
	 */
	public AccountCreateException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
