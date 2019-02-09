package jp.co.ha.common.exception;

/**
 * DBアクセスの例外クラス
 *
 */
public class DataBaseException extends BaseException {

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
	public DataBaseException(BaseErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
