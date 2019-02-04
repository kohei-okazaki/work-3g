package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;

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
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 */
	public AccountCreateException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
