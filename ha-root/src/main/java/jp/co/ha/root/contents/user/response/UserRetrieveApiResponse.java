package jp.co.ha.root.contents.user.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.root.contents.tools.response.RoleMtListApiResponse.Role;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * ユーザ情報取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class UserRetrieveApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** ログインID */
    @JsonProperty("seq_login_id")
    private Integer seqLoginId;
    /** 権限リスト */
    @JsonProperty("roles")
    private List<Role> roles;
    /** 削除フラグ */
    @JsonProperty("delete_flag")
    private String deleteFlag;
    /** パスワード有効期限 */
    @JsonProperty("password_expire")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd", timezone = "Asia/Tokyo")
    private LocalDate passwordExpire;
    /** 備考 */
    @JsonProperty("remarks")
    private String remarks;

    /**
     * seqLoginIdを返す
     *
     * @return seqLoginId
     */
    public Integer getSeqLoginId() {
        return seqLoginId;
    }

    /**
     * seqLoginIdを設定する
     *
     * @param seqLoginId
     *     ログインID
     */
    public void setSeqLoginId(Integer seqLoginId) {
        this.seqLoginId = seqLoginId;
    }

    /**
     * rolesを返す
     *
     * @return roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * rolesを設定する
     *
     * @param roles
     *     権限リスト
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * deleteFlagを返す
     *
     * @return deleteFlag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * deleteFlagを設定する
     *
     * @param deleteFlag
     *     削除フラグ
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * passwordExpireを返す
     *
     * @return passwordExpire
     */
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * passwordExpireを設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(LocalDate passwordExpire) {
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
