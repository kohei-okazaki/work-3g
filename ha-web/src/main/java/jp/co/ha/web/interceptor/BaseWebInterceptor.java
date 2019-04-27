package jp.co.ha.web.interceptor;

import java.util.function.Predicate;

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
	 * @return 判定結果
	 */
	protected Predicate<Object> isStaticResource() {
		return e -> e instanceof ResourceHttpRequestHandler;
	}
}
