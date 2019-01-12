package jp.co.ha.common.web.interceptor;

import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * Webの基底インターセプタ－
 *
 */
public abstract class BaseWebInterceptor implements BaseInterceptor {

	/**
	 * インターセプターで検査対象のリソースかどうか判定する<br>
	 * javascriptなどの静的リソースはtrueを返す<br>
	 *
	 * @param handler
	 *     ハンドラー
	 * @return 判定結果
	 */
	protected boolean isStaticResource(Object handler) {
		return handler instanceof ResourceHttpRequestHandler;
	}
}
