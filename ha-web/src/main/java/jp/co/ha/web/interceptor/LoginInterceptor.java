package jp.co.ha.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.SessionIllegalException;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.system.annotation.NonAuth;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseInterceptor;

public class LoginInterceptor extends BaseInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

	@Autowired
	private SessionManageService sessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 静的リソースの場合は認証不要
        if (handler instanceof ResourceHttpRequestHandler) {
              return true;
        }

		 if (BeanUtil.notNull(getAuthCheckAnnotation(handler))) {
			 LOG.info("ログイン情報チェック対象ではありません");
			 return true;
		 } else {
			 LOG.info("ログイン情報チェック対象です");
			 boolean res = StringUtil.isEmpty(sessionService.getValue(request.getSession(), "userId", String.class));
			 if (res) {
				 throw new SessionIllegalException(ErrorCode.ILLEGAL_ACCESS_ERROR, "ユーザIDがありません");
			 }
			 return true;
		 }
	}

	/**
	 * ログインチェック対象アノテーションを取得
	 * @param handler
	 * @return
	 */
	private NonAuth getAuthCheckAnnotation(Object handler) {
		 HandlerMethod handleMethod = (HandlerMethod) handler;
		 return handleMethod.getMethod().getAnnotation(NonAuth.class);
	}

}
