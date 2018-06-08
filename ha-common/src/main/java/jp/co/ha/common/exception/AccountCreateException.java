package jp.co.ha.common.exception;

/**
 * アカウント作成例外クラス<br>
 *
 */
public class AccountCreateException extends BaseAppException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ<br>
	 *
	 * @param errorCode
	 *            エラーコード
	 * @param detail
	 *            詳細
	 */
	public AccountCreateException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
