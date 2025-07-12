package jp.co.ha.business.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.interceptor.BaseWebInterceptor;

/**
 * ダッシュボード認証チェックを行うインターセプター
 * <ul>
 * <li>ログイン認証チェック</li>
 * <li>多重送信トークンチェック</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Component
public class DashboardAuthInterceptor extends BaseWebInterceptor {

    /** {@linkplain SessionComponent} */
    @Autowired
    private SessionComponent sessionComponent;
    /** {@linkplain Sha256HashEncoder} */
    @Sha256
    @Autowired
    private HashEncoder encoder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        if (isStaticResource().test(handler)) {
            // 静的リソースの場合は認証不要
            return true;
        }

        if (isLoginAuthCheck(handler)) {
            // ログイン情報のチェック対象の場合
            // LOG.warn("url=" + request.getRequestURI());
            sessionComponent.getValue(request.getSession(), "seqUserId", Long.class)
                    .orElseThrow(() -> new SystemException(
                            DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));
        }

        if (isMultiSubmitTokenCheck(handler)) {
            // 多重送信トークンチェックを行う
            String multiSubmitToken = sessionComponent
                    .getValue(request.getSession(), MultiSubmitToken.TOKEN_NAME,
                            String.class)
                    .orElseThrow(() -> new SystemException(
                            DashboardErrorCode.MULTI_SUBMIT_ERROR, "不正リクエストエラーです"));
            if (StringUtil.isEmpty(multiSubmitToken)) {
                throw new SystemException(DashboardErrorCode.MULTI_SUBMIT_ERROR,
                        "不正リクエストエラーです");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {

        if (isStaticResource().test(handler)) {
            // 静的リソースの場合は認証不要
            return;
        }

        if (isMultiSubmitTokenCheck(handler)) {
            // 多重送信トークンチェック後、トークンを削除する
            sessionComponent.removeValue(request.getSession(),
                    MultiSubmitToken.TOKEN_NAME);
        }

        if (isMultiSubmitTokenFactory(handler)) {
            // 多重送信トークンを作成する
            String multiSubmitToken = encoder.encode(StringUtil.getRandamStr(10), "");
            sessionComponent.setValue(request.getSession(), MultiSubmitToken.TOKEN_NAME,
                    multiSubmitToken);
        }
    }

    /**
     * ログイン情報をチェックするかどうかを返す<br>
     * <ul>
     * <li>ログイン情報をチェックする場合、true</li>
     * <li>ログイン情報をチェックしない場合、false</li>
     * </ul>
     *
     * @param handler
     *     ハンドラー
     * @return 判定結果
     */
    private boolean isLoginAuthCheck(Object handler) {
        return !((HandlerMethod) handler).getMethod().isAnnotationPresent(NonAuth.class);
    }

    /**
     * 多重送信チェックを行うかどうかを返す
     *
     * @param handler
     *     ハンドラー
     * @return 判定結果
     */
    private boolean isMultiSubmitTokenCheck(Object handler) {
        MultiSubmitToken multiSubmitToken = ((HandlerMethod) handler).getMethod()
                .getAnnotation(MultiSubmitToken.class);
        if (BeanUtil.isNull(multiSubmitToken)) {
            return false;
        }
        return multiSubmitToken.check();
    }

    /**
     * 多重送信トークンを作成するかどうかを返す
     *
     * @param handler
     *     ハンドラー
     * @return 判定結果
     */
    private boolean isMultiSubmitTokenFactory(Object handler) {
        MultiSubmitToken multiSubmitToken = ((HandlerMethod) handler).getMethod()
                .getAnnotation(MultiSubmitToken.class);
        if (BeanUtil.isNull(multiSubmitToken)) {
            return false;
        }
        return multiSubmitToken.factory();
    }

}
