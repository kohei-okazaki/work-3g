package jp.co.ha.business.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.SessionIllegalException;
import jp.co.ha.common.interceptor.BaseWebInterceptor;
import jp.co.ha.common.system.SessionManageService;

/**
 * ログイン情報のチェックを行うインターセプター
 *
 */
public class AuthInterceptor extends BaseWebInterceptor {

	/** session管理サービス */
	@Autowired
	private SessionManageService sessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (isStaticResource().test(handler)) {
			// 静的リソースの場合は認証不要
			return true;
		}
		if (isLoginAuthCheck(handler)) {
			// ログイン情報のチェック対象の場合
			sessionService.getValue(request.getSession(), "userId", String.class)
					.orElseThrow(() -> new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
		}
		return true;
	}

	/**
	 * ログイン情報をチェックするかどうかを返す
	 *
	 * @param handler
	 *     ハンドラー
	 * @return 判定結果
	 */
	private boolean isLoginAuthCheck(Object handler) {
		return !((HandlerMethod) handler).getMethod().isAnnotationPresent(NonAuth.class);
	}

}
