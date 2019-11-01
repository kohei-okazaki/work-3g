package jp.co.ha.common.exception;

import jp.co.ha.common.log.type.LogLevel;

/**
 * エラーコードの基底インターフェース
 * 
 * @since 1.0
 */
public interface BaseErrorCode {

	/**
	 * エラーコード(外部用)を返却
	 *
	 * @return エラーコード(外部用)
	 */
	String getOuterErrorCode();

	/**
	 * ログレベルを返却
	 *
	 * @return ログレベル
	 */
	LogLevel getLogLevel();
}
