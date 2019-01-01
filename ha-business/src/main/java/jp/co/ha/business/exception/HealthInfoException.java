package jp.co.ha.business.exception;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;

/**
 * 健康情報例外クラス
 *
 */
public class HealthInfoException extends BaseException {

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
	public HealthInfoException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
