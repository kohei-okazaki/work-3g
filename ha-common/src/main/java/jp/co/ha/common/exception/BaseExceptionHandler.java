package jp.co.ha.common.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.log.type.LogLevel;

/**
 * Exceptionハンドラー基底インターフェース<br>
 *
 */
public interface BaseExceptionHandler extends HandlerExceptionResolver {

	/** ロガー */
	static final Logger LOG = LoggerFactory.getLogger(BaseExceptionHandler.class);

	/**
	 * エラーメッセージを返す<br>
	 *
	 * @param e
	 *     例外クラス
	 * @return
	 */
	String buildErrorMessage(Exception e);

	/**
	 * 指定したエラーメッセージのログを出力する<br>
	 *
	 * @param errorMessage
	 *     エラーメッセージ
	 * @param e
	 *     例外クラス
	 */
	default void outLog(String errorMessage, Exception e) {

		if (e instanceof BaseException) {
			BaseException be = (BaseException) e;
			if (LogLevel.ERROR.is(be.getErrorCode().getLogLevel())) {
				// ERRORの場合
				LOG.error(errorMessage, be);
			} else if (LogLevel.WARN.is(be.getErrorCode().getLogLevel())) {
				// WARNの場合
				LOG.warn(errorMessage, be);
			}
		} else {
			// 予期せぬエラー
			LOG.error(errorMessage, e);
		}

	}
}
