package jp.co.ha.business.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.SessionIllegalException;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseInterceptor;

/**
 * ログイン情報のチェックを行うinterceptor
 *
 */
public class LoginInterceptor extends BaseInterceptor {

	/** session管理サービス */
	@Autowired
	private SessionManageService sessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (isSkipResource(handler)) {
			// 静的リソースの場合は認証不要
			return true;
		}
		if (isLoginAuthCheck(handler)) {
			// ログイン情報のチェック対象の場合
			boolean res = StringUtil.isEmpty(sessionService.getValue(request.getSession(), "userId", String.class));
			if (res) {
				throw new SessionIllegalException(ErrorCode.ILLEGAL_ACCESS_ERROR, "ユーザIDがありません");
			}
		}
		return true;
	}

	/**
	 * ログイン情報をチェックするかどうかを返す<br>
	 *
	 * @param handler
	 * @return
	 */
	private boolean isLoginAuthCheck(Object handler) {
		NonAuth annotation = ((HandlerMethod) handler).getMethod().getAnnotation(NonAuth.class);
		return BeanUtil.isNull(annotation);
	}

}
