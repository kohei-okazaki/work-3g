package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.BaseException;

/**
 * アカウント作成例外クラス
 *
 */
public class AccountCreateException extends BaseException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 *
	 * @param e
	 *     例外クラス
	 */
	public AccountCreateException(Exception e) {
		super(e);
	}

	/**
	 * コンストラクタ
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 */
	public AccountCreateException(BaseErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	/**
	 * コンストラクタ
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 * @param e
	 *     例外クラス
	 */
	public AccountCreateException(BaseErrorCode errorCode, String detail, Exception e) {
		super(errorCode, detail, e);
	}
}
