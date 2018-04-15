package jp.co.ha.common.exception;

/**
 * アカウント設定例外クラス<br>
 *
 */
public class AccountSettingException extends BaseAppException {

	/**
	 * コンストラクタ<br>
	 * @param errorCode
	 * @param detail
	 */
	public AccountSettingException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
