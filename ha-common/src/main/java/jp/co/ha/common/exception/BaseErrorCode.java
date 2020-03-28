package jp.co.ha.common.exception;

import jp.co.ha.common.log.Logger.LogLevel;

/**
 * エラーコードの基底インターフェース
 *
 * @version 1.0.0
 */
public interface BaseErrorCode {

    /**
     * エラーコード(外部用)を返す
     *
     * @return エラーコード(外部用)
     */
    String getOuterErrorCode();

    /**
     * {@linkplain LogLevel}を返す
     *
     * @return ログレベル
     */
    LogLevel getLogLevel();
}
