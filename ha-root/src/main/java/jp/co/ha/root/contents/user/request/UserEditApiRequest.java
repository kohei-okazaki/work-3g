package jp.co.ha.root.contents.user.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.root.type.RootRoleType;

/**
 * ユーザ編集APIリクエストクラス
 *
 * @version 1.0.0
 */
public class UserEditApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** 権限 */
    @JsonProperty("roles")
    @Required(message = "roles is required")
    private List<RootRoleType> roles;
    /** 削除フラグ */
    @JsonProperty("delete_flag")
    @Required(message = "delete_flag is required")
    private boolean deleteFlag;
    /** パスワード */
    @JsonProperty("password")
    @Required(message = "password is required")
    private String password;
    /** パスワード有効期限 */
    @JsonProperty("password_expire")
    @Required(message = "password_expire is required")
    private String passwordExpire;
    /** 備考 */
    @JsonProperty("remarks")
    private String remarks;

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
     *     権限
     */
    public void setRoles(List<RootRoleType> roles) {
        this.roles = roles;
    }

    /**
     * deleteFlagを返す
     *
     * @return deleteFlag
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * deleteFlagを設定する
     *
     * @param deleteFlag
     *     削除フラグ
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * passwordを返す
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * passwordを設定する
     *
     * @param password
     *     パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * passwordExpireを返す
     *
     * @return passwordExpire
     */
    public String getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * passwordExpireを設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(String passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    /**
     * remarksを返す
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * remarksを設定する
     *
     * @param remarks
     *     備考
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
