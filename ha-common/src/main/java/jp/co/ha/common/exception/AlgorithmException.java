package jp.co.ha.common.exception;

/**
 * アルゴリズム作成例外クラス<br>
 *
 */
public class AlgorithmException extends BaseAppException {

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
	public AlgorithmException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

}
