package jp.co.ha.dashboard.login.auth;

import static jp.co.ha.business.exception.DashboardErrorCode.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.login.LoginCheck;
import jp.co.ha.business.login.LoginCheckResult;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.User;

/**
 * 健康管理ダッシュボード認証Provider
 *
 * @version 1.0.0
 */
@Component
public class DashboardAuthenticationProvider implements AuthenticationProvider {

    /** UserComponent */
    @Autowired
    private UserComponent userComponent;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String mailAddress = authentication.getName();
        String password = authentication.getCredentials() == null ? ""
                : authentication.getCredentials().toString();

        try {
            Optional<User> user = userComponent.findByMailAddress(mailAddress);
            LoginCheckResult checkResult = new LoginCheck().check(user, password);
            if (checkResult.hasError()) {
                throw toAuthenticationException(checkResult.getErrorCode());
            }

            DashboardAuthInfo principal = new DashboardAuthInfo(user.get());
            return new UsernamePasswordAuthenticationToken(principal, null,
                    principal.getAuthorities());
        } catch (BaseException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class
                .isAssignableFrom(authentication);
    }

    /**
     * 認証エラー時のエラーコードから例外クラスを返却する
     * 
     * @param errorCode
     *     エラーコード
     * @return AuthenticationException
     */
    private AuthenticationException toAuthenticationException(
            DashboardErrorCode errorCode) {

        if (ACCOUNT_EXIST == errorCode) {
            return new UsernameNotFoundException(errorCode.getValue());
        } else if (ACCOUNT_INVALID_PASSWORD == errorCode) {
            return new BadCredentialsException(errorCode.getValue());
        } else if (ACCOUNT_DELETE == errorCode) {
            return new DisabledException(errorCode.getValue());
        } else if (ACCOUNT_EXPIRED == errorCode) {
            return new CredentialsExpiredException(errorCode.getValue());
        }
        return new AuthenticationServiceException(errorCode.getValue());
    }

}
