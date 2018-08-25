package jp.co.ha.common.exception;

import jp.co.ha.common.log.AppLogger;
import jp.co.ha.common.log.LogLevel;
import jp.co.ha.common.log.LoggerFactory;

/**
 * アプリ内で扱う基底例外クラス<br>
 *
 */
public abstract class BaseException extends Exception {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;
	/** ロガー */
	private final AppLogger LOG = LoggerFactory.getAppLogger(this.getClass());

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

	/**
	 * ログ出力を行う<br>
	 *
	 * @param errorCode
	 *     エラーコード
	 */
	private void outLog(ErrorCode errorCode) {
		if (LogLevel.WARN == errorCode.getLogLevel()) {
			LOG.warn(detail + "(" + errorCode.getOuterErrorCode() + ")", this);
		} else if (LogLevel.ERROR == errorCode.getLogLevel()) {
			LOG.error(detail + "(" + errorCode.getOuterErrorCode() + ")", this);
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
