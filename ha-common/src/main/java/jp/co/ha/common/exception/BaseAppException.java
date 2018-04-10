package jp.co.ha.common.exception;

import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;

/**
 * アプリ内で扱う基底例外クラス<br>
 *
 */
public abstract class BaseAppException extends Exception {

	/** エラーコード */
	private ErrorCode errorCode;
	/** エラーメッセージ */
	private String errorMessage;

	/**
	 * 例外コンストラクタ<br>
	 */
	public BaseAppException() {
	}

	/**
	 * 例外コンストラクタ<br>
	 * @param errorCode
	 * @param errorMessage
	 */
	public BaseAppException(ErrorCode errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * errorCodeを返す
	 * @return errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * errorMessageを返す
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * エラーメッセージを組み立てて返す<br>
	 */
	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(StringUtil.SPACE);
		joiner.add(this.errorCode.getLogLevel());
		joiner.add(this.errorCode.getErrorCode());
		joiner.add(this.errorMessage);
		return joiner.toString();
	}

}
