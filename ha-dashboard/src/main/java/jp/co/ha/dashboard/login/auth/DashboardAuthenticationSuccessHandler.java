package jp.co.ha.dashboard.login.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jp.co.ha.common.system.SessionComponent;

/**
 * 健康管理ダッシュボード認証成功時のハンドラークラス
 *
 * @version 1.0.0
 */
@Component
public class DashboardAuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    /** セッションキー: user id */
    public static final String SESSION_KEY_SEQ_USER_ID = "seqUserId";

    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;

    /**
     * コンストラクタ
     */
    public DashboardAuthenticationSuccessHandler() {
        setAlwaysUseDefaultTargetUrl(true);
        setDefaultTargetUrl("/top");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if (authentication.getPrincipal() instanceof DashboardAuthInfo authInfo) {
            sessionComponent.setValue(request.getSession(), SESSION_KEY_SEQ_USER_ID,
                    authInfo.getSeqUserId());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
