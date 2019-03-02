package jp.co.ha.business.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.SessionIllegalException;
import jp.co.ha.common.interceptor.BaseWebInterceptor;
import jp.co.ha.common.interceptor.annotation.CsrfToken;
import jp.co.ha.common.system.HashEncoder;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.system.impl.Sha256PasswordEncoder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * 認証チェックを行うインターセプター
 * <ul>
 * <li>ログイン認証チェック</li>
 * <li>CSRFトークンチェック</li>
 * </ul>
 *
 */
public class AuthInterceptor extends BaseWebInterceptor {

	/** session管理サービス */
	@Autowired
	private SessionManageService sessionService;
	/** SHA-256 ハッシュ値生成 */
//	@Sha256
//	@Autowired
//	private HashEncoder encoder;

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
					.orElseThrow(() -> new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));
		}

		if (isCsrfTokenCheck(handler)) {
			// CSRFチェックを行う
			String sessionCsrfToken = sessionService.getValue(request.getSession(), "csrfToken", String.class)
					.orElseThrow(() -> new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));
			if (StringUtil.isEmpty(sessionCsrfToken)) {
				throw new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです");
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (isStaticResource().test(handler)) {
			return;
		}

		if (isCsrfTokenCheck(handler)) {
			// CSRFトークンチェック後、トークンを削除する
			sessionService.removeValue(request.getSession(), "csrfToken");
		}

		if (isCsrfTokenFactory(handler)) {
			// CSRFトークンを作成する
			String random = RandomStringUtils.randomAlphabetic(10);
			HashEncoder encoder = new Sha256PasswordEncoder();
			String csrfToken = encoder.encode(random, "");
			sessionService.setValue(request.getSession(), "csrfToken", csrfToken);
		}
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

	/**
	 * CSRFチェックを行うかどうかを返す
	 *
	 * @param handler
	 *     ハンドラー
	 * @return 判定結果
	 */
	private boolean isCsrfTokenCheck(Object handler) {
		CsrfToken csrfToken = ((HandlerMethod) handler).getMethod().getAnnotation(CsrfToken.class);
		if (BeanUtil.isNull(csrfToken)) {
			return false;
		}
		return csrfToken.check();
	}

	/**
	 * CSRFトークンを作成するかどうかを返す
	 *
	 * @param handler
	 *     ハンドラー
	 * @return 判定結果
	 */
	private boolean isCsrfTokenFactory(Object handler) {
		CsrfToken csrfToken = ((HandlerMethod) handler).getMethod().getAnnotation(CsrfToken.class);
		if (BeanUtil.isNull(csrfToken)) {
			return false;
		}
		return csrfToken.factocy();
	}

}
