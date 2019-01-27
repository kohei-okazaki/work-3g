package jp.co.ha.common.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.log.type.LogLevel;

/**
 * Exceptionハンドラー基底インターフェース
 *
 */
public interface BaseExceptionHandler extends HandlerExceptionResolver {

	/** ロガー */
	static final Logger LOG = LoggerFactory.getLogger(BaseExceptionHandler.class);

	/**
	 * エラーメッセージを返す
	 *
	 * @param e
	 *     例外クラス
	 * @return エラーメッセージ
	 */
	String buildErrorMessage(Exception e);

	/**
	 * 指定したエラーメッセージのログを出力する
	 *
	 * @param errorMessage
	 *     エラーメッセージ
	 * @param e
	 *     例外クラス
	 */
	default void outLog(String errorMessage, Exception e) {

		if (e instanceof BaseException) {
			BaseException be = (BaseException) e;
			LogLevel level = be.getErrorCode().getLogLevel();
			if (LogLevel.ERROR.is(level)) {
				// ERRORの場合
				LOG.error(errorMessage, be);
			} else if (LogLevel.WARN.is(level)) {
				// WARNの場合
				LOG.warn(errorMessage, be);
			}
		} else {
			// 予期せぬエラー
			LOG.error(errorMessage, e);
		}

	}
}
