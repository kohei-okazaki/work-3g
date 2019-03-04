package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseErrorCode;
import jp.co.ha.common.exception.BaseException;

/**
 * 健康情報例外クラス
 *
 */
public class HealthInfoException extends BaseException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 *
	 * @param e
	 *     例外クラス
	 */
	public HealthInfoException(Exception e) {
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
	public HealthInfoException(BaseErrorCode errorCode, String detail) {
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
	public HealthInfoException(BaseErrorCode errorCode, String detail, Exception e) {
		super(errorCode, detail, e);
	}
}
