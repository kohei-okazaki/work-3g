package jp.co.ha.common.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Exceptionハンドラー基底インターフェース<br>
 *
 */
public interface BaseExceptionHandler extends HandlerExceptionResolver {

	/**
	 * エラーメッセージを返す<br>
	 *
	 * @param e
	 *            例外クラス
	 * @return
	 */
	String buildErrorMessage(Exception e);

}
