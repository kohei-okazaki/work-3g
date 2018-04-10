package jp.co.ha.common.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Exceptionハンドラー基底インターフェース<br>
 *
 */
public interface BaseExceptionHandler extends HandlerExceptionResolver {

	/** 例外時に表示するファイル名 */
	public static final String ERROR_PAGE= "error";

}
