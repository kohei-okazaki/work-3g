package jp.co.ha.business.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.web.interceptor.BaseWebInterceptor;

/**
 * Webのリクエストインターセプター
 *
 */
public class RequestInterceptor extends BaseWebInterceptor {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (isStaticResource(handler)) {
			// 静的リソースの場合は認証不要
			return true;
		}
		Method method = ((HandlerMethod) handler).getMethod();
		LOG.info("---> START " + method.getDeclaringClass() + "#" + method.getName());

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		if (isStaticResource(handler)) {
			// 静的リソースの場合は認証不要
			return;
		}
		Method method = ((HandlerMethod) handler).getMethod();
		LOG.info("---> END " + method.getDeclaringClass().getName() + "#" + method.getName());

	}
}
