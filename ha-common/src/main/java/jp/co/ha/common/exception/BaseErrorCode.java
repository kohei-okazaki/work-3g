package jp.co.ha.common.exception;

import jp.co.ha.common.log.type.LogLevel;

/**
 * エラーコードの基底インターフェース
 *
 */
public interface BaseErrorCode {

	String getOuterErrorCode();

	LogLevel getLogLevel();
}
