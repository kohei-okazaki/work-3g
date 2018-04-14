package jp.co.ha.common.exception;

/**
 * 健康情報例外クラス<br>
 *
 */
public class HealthInfoException extends BaseAppException {

	/**
	 * コンストラクタ<br>
	 * @param errorCode
	 * @param detail
	 */
	public HealthInfoException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
