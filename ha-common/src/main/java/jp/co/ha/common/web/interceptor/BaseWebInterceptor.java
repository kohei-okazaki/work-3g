package jp.co.ha.common.web.interceptor;

import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * Webの基底インターセプター<br>
 *
 */
public abstract class BaseWebInterceptor implements BaseInterceptor {

	/**
	 * インターセプターで検査対象のリソースかどうか判定する<br>
	 * javascriptなどの静的リソースはtrueを返す<br>
	 *
	 * @param handler
	 * @return
	 */
	protected boolean isStaticResource(Object handler) {
		return handler instanceof ResourceHttpRequestHandler;
	}
}
