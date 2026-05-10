package jp.co.ha.dashboard.login.auth;

import static jp.co.ha.business.exception.DashboardErrorCode.*;

import java.io.IOException;
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jp.co.ha.business.exception.DashboardErrorCode;

/**
 * 健康管理ダッシュボード認証失敗時のハンドラークラス
 *
 * @version 1.0.0
 */
@Component
public class DashboardAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    /** エラーメッセージキー */
    public static final String LOGIN_ERROR_MESSAGE_KEY = "loginErrorMessage";

    /** message source */
    @Autowired
    private MessageSource messageSource;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        DashboardErrorCode errorCode = resolveErrorCode(exception);
        String errorMessage = messageSource.getMessage(errorCode.getOuterErrorCode(),
                null, Locale.getDefault());
        request.getSession().setAttribute(LOGIN_ERROR_MESSAGE_KEY, errorMessage);

        response.sendRedirect(request.getContextPath() + "/login?error=true");
    }

    /**
     * エラーコードの解析
     *
     * @param exception
     *     authentication exception
     * @return dashboard error code
     */
    private DashboardErrorCode resolveErrorCode(AuthenticationException exception) {
        if (exception instanceof UsernameNotFoundException) {
            return ACCOUNT_EXIST;
        } else if (exception instanceof BadCredentialsException) {
            return ACCOUNT_INVALID_PASSWORD;
        } else if (exception instanceof DisabledException) {
            return ACCOUNT_DELETE;
        } else if (exception instanceof CredentialsExpiredException) {
            return ACCOUNT_EXPIRED;
        }
        return ACCOUNT_ILLEGAL;
    }

}
