package jp.co.ha.common.exception;

/**
 * アカウント設定例外クラス<br>
 *
 */
public class AccountSettingException extends BaseException {

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
	public AccountSettingException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
