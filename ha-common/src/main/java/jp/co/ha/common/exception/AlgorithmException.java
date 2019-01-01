package jp.co.ha.common.exception;

/**
 * アルゴリズム作成例外クラス
 *
 */
public class AlgorithmException extends BaseException {

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
	public AlgorithmException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

}
