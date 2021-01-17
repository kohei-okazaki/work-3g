package jp.co.ha.root.contents.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.db.entity.RootLoginInfo;

/**
 * 認証情報<br>
 * 基本的なフィールドは管理者サイトユーザログイン情報と同じため継承する
 *
 * @version 1.0.0
 */
public class AuthInfo extends RootLoginInfo implements UserDetails {

    /** serialVersionUID */
    private static final long serialVersionUID = 751942013999517940L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getSeqRootLoginInfoId() == null ? null
                : super.getSeqRootLoginInfoId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return DateTimeUtil.isAfter(super.getPasswordExpire(),
                DateTimeUtil.toLocalDate(DateTimeUtil.getSysDate()), true);
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        CommonFlag deleteFlag = CommonFlag.of(super.getDeleteFlag());
        return CommonFlag.FALSE == deleteFlag;
    }

}
