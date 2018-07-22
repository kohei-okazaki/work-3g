package jp.co.ha.common.exception;

/**
 * DBアクセスの例外クラス<br>
 *
 */
public class DataBaseException extends BaseAppException {

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
	public DataBaseException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
}
