package jp.co.ha.common.exception;

import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;

/**
 * アプリ内で扱う基底例外クラス<br>
 *
 */
public abstract class BaseAppException extends Exception {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

	/** エラーコード */
	private ErrorCode errorCode;
	/** 詳細 */
	private String detail;

	/**
	 * コンストラクタ<br>
	 */
	public BaseAppException() {
	}

	/**
	 * コンストラクタ<br>
	 *
	 * @param errorCode
	 *            エラーコード
	 * @param detail
	 *            詳細
	 */
	public BaseAppException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
	}

	/**
	 * errorCodeを返す
	 *
	 * @return errorCode エラーコード
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * detailを返す<br>
	 *
	 * @return detail 詳細
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * エラーメッセージを組み立てて返す<br>
	 *
	 * @return 組み立てたエラーメッセージ
	 */
	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(StringUtil.SPACE);
		joiner.add(this.errorCode.getLogLevel());
		joiner.add(this.errorCode.getErrorCode());
		joiner.add(this.detail);
		return joiner.toString();
	}

}
