package jp.co.ha.common.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * Interceptorの基底クラス<br>
 *
 */
public abstract class BaseInterceptor implements HandlerInterceptor {

	/**
	 * インターセプターで検査対象のリソースかどうか判定する<br>
	 * @param handler
	 * @return
	 */
	protected boolean isStaticResource(Object handler) {
		return handler instanceof ResourceHttpRequestHandler;
	}
}
