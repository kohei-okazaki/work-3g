package jp.co.ha.root.contents.user.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.root.type.RootRoleType;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * ユーザ編集APIリクエストクラス
 *
 * @version 1.0.0
 */
public class UserEditApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** 権限 */
    @JsonProperty("roles")
    private List<RootRoleType> roles;

    /**
     * rolesを返す
     *
     * @return roles
     */
    public List<RootRoleType> getRoles() {
        return roles;
    }

    /**
     * rolesを設定する
     *
     * @param roles
     */
    public void setRoles(List<RootRoleType> roles) {
        this.roles = roles;
    }

}
