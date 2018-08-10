package jp.co.ha.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.ha.common.web.BaseInterceptor;

public class LoginInterceptor extends BaseInterceptor {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		System.out.println("uri ---> " + uri);
		System.out.println("url ---> " + url);
		return true;
	}
}
