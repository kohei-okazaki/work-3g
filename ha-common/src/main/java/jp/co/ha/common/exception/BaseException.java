package jp.co.ha.common.exception;

import org.slf4j.Logger;

import jp.co.ha.common.log.LoggerFactory;

/**
 * アプリ内で扱う基底例外クラス<br>
 *
 */
public abstract class BaseException extends Exception {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;
	/** ロガー */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/** エラーコード */
	private ErrorCode errorCode;
	/** 詳細 */
	private String detail;

	/**
	 * コンストラクタ<br>
	 *
	 * @param errorCode
	 *     エラーコード
	 * @param detail
	 *     詳細
	 */
	public BaseException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
		this.outLog(errorCode);
	}

	private void outLog(ErrorCode errorCode) {
		if (LogLevel.DEBUG == errorCode.getLogLevel()) {
			LOG.debug(detail);
		} else if (LogLevel.INFO == errorCode.getLogLevel()) {
			LOG.info(detail);
		} else if (LogLevel.WARN == errorCode.getLogLevel()) {
			LOG.warn(detail);
		} else if (LogLevel.ERROR == errorCode.getLogLevel()) {
			LOG.error(detail);
		}
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

}
