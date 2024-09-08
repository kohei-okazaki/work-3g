package jp.co.ha.db.entity.composite;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.db.entity.RootLoginInfoKey;

/**
 * 以下のテーブルの複合Entity
 * <ul>
 * <li>管理者サイトユーザログイン情報</li>
 * <li>管理者サイトユーザ権限管理マスタ</li>
 * <li>管理者サイトユーザ権限詳細マスタ</li>
 * <li>管理者サイトユーザ権限マスタ</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Entity
public class CompositeRootUserInfo extends RootLoginInfoKey {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 1L;

    /** 管理者サイトユーザ権限管理マスタID */
    private Long seqRootUserRoleMngMtId;
    /** 管理者サイトユーザ権限詳細マスタID */
    private Long seqRootUserRoleDetailMtId;
    /** 削除フラグ */
    private String deleteFlag;
    /** パスワード */
    @Mask
    private String password;
    /** 登録日時 */
    private LocalDate passwordExpire;
    /** 備考 */
    private String remarks;
    /** ユーザ権限 */
    private String role;
    /** ユーザ権限名 */
    private String roleName;
    /** 登録日時 */
    private LocalDateTime regDate;
    /** 更新日時 */
    private LocalDateTime updateDate;

    /**
     * seqRootUserRoleMngMtIdを返す
     *
     * @return seqRootUserRoleMngMtId
     */
    public Long getSeqRootUserRoleMngMtId() {
        return seqRootUserRoleMngMtId;
    }

    /**
     * seqRootUserRoleMngMtIdを設定する
     *
     * @param seqRootUserRoleMngMtId
     *     管理者サイトユーザ権限管理マスタID
     */
    public void setSeqRootUserRoleMngMtId(Long seqRootUserRoleMngMtId) {
        this.seqRootUserRoleMngMtId = seqRootUserRoleMngMtId;
    }

    /**
     * seqRootUserRoleDetailMtIdを返す
     *
     * @return seqRootUserRoleDetailMtId
     */
    public Long getSeqRootUserRoleDetailMtId() {
        return seqRootUserRoleDetailMtId;
    }

    /**
     * seqRootUserRoleDetailMtIdを設定する
     *
     * @param seqRootUserRoleDetailMtId
     *     管理者サイトユーザ権限詳細マスタID
     */
    public void setSeqRootUserRoleDetailMtId(Long seqRootUserRoleDetailMtId) {
        this.seqRootUserRoleDetailMtId = seqRootUserRoleDetailMtId;
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

    /**
     * roleを返す
     *
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * roleを設定する
     *
     * @param role
     *     権限
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * roleNameを返す
     *
     * @return roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * roleNameを設定する
     *
     * @param roleName
     *     権限名
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * regDateを返す
     *
     * @return regDate
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * regDateを設定する
     *
     * @param regDate
     *     登録日時
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * updateDateを返す
     *
     * @return updateDate
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * updateDateを設定する
     *
     * @param updateDate
     *     更新日時
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

}
