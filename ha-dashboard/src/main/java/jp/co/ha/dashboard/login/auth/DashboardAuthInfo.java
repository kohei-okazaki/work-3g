package jp.co.ha.dashboard.login.auth;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.User;

/**
 * 健康管理ダッシュボード認証情報
 *
 * @version 1.0.0
 */
public class DashboardAuthInfo extends User implements UserDetails {

    /** serialVersionUID */
    private static final long serialVersionUID = 8612280150408665188L;

    /**
     * コンストラクタ
     *
     * @param user
     *     user
     */
    public DashboardAuthInfo(User user) {
        BeanUtil.copy(user, this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return getMailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (getPasswordExpire() == null) {
            return false;
        }
        return DateTimeUtil.isAfter(getPasswordExpire(),
                DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate()), true);
    }

    @Override
    public boolean isEnabled() {
        return !Boolean.TRUE.equals(getDeleteFlag());
    }

}
