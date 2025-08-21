package jp.co.ha.root.contents.user.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Future;
import jp.co.ha.common.validator.annotation.Length;
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

    /** 権限リスト */
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
    @Future(message = "password_expire is must be future")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
    private LocalDate passwordExpire;
    /** 備考 */
    @JsonProperty("remarks")
    @Length(length = 256, mode = LengthMode.LESS_EQUAL, message = "remarks is less equal 256 byte")
    private String remarks;

    /**
     * 権限リストを返す
     *
     * @return roles
     */
    public List<RootRoleType> getRoles() {
        return roles;
    }

    /**
     * 権限リストを設定する
     *
     * @param roles
     *     権限リスト
     */
    public void setRoles(List<RootRoleType> roles) {
        this.roles = roles;
    }

    /**
     * 削除フラグを返す
     *
     * @return deleteFlag
     */
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 削除フラグを設定する
     *
     * @param deleteFlag
     *     削除フラグ
     */
    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * パスワードを返す
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定する
     *
     * @param password
     *     パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * パスワード有効期限を返す
     *
     * @return passwordExpire
     */
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * パスワード有効期限を設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(LocalDate passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    /**
     * 備考を返す
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 備考を設定する
     *
     * @param remarks
     *     備考
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
