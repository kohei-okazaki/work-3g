package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;

/**
 * アカウント設定例外クラス
 *
 */
public class AccountSettingException extends BaseException {

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
	public AccountSettingException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
