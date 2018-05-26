package jp.co.ha.common.exception;

/**
 * DBアクセスの例外クラス<br>
 *
 */
public class DBException extends BaseAppRuntimeException {

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
	public DBException(ErrorCode errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

}
