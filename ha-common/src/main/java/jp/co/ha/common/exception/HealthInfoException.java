package jp.co.ha.common.exception;

/**
 * 健康情報例外クラス<br>
 *
 */
public class HealthInfoException extends BaseAppException {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ<br>
	 * @param errorCode
	 * @param detail
	 */
	public HealthInfoException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
