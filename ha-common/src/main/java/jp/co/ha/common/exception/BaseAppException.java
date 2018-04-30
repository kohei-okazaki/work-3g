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
	 * 例外コンストラクタ<br>
	 */
	public BaseAppException() {
	}

	/**
	 * 例外コンストラクタ<br>
	 * @param errorCode
	 * @param detail
	 */
	public BaseAppException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
	}

	/**
	 * errorCodeを返す
	 * @return errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * errorCodeを設定する<br>
	 * @param errorCode
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * detailを返す<br>
	 * @return detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * detailを設定する<br>
	 * @param detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * エラーメッセージを組み立てて返す<br>
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
