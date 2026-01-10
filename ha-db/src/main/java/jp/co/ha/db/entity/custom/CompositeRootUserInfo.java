package jp.co.ha.db.entity.custom;

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
    private Boolean deleteFlag;
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
     * 管理者サイトユーザ権限管理マスタIDを返す
     *
     * @return seqRootUserRoleMngMtId
     */
    public Long getSeqRootUserRoleMngMtId() {
        return seqRootUserRoleMngMtId;
    }

    /**
     * 管理者サイトユーザ権限管理マスタIDを設定する
     *
     * @param seqRootUserRoleMngMtId
     *     管理者サイトユーザ権限管理マスタID
     */
    public void setSeqRootUserRoleMngMtId(Long seqRootUserRoleMngMtId) {
        this.seqRootUserRoleMngMtId = seqRootUserRoleMngMtId;
    }

    /**
     * 管理者サイトユーザ権限詳細マスタIDを返す
     *
     * @return seqRootUserRoleDetailMtId
     */
    public Long getSeqRootUserRoleDetailMtId() {
        return seqRootUserRoleDetailMtId;
    }

    /**
     * 管理者サイトユーザ権限詳細マスタIDを設定する
     *
     * @param seqRootUserRoleDetailMtId
     *     管理者サイトユーザ権限詳細マスタID
     */
    public void setSeqRootUserRoleDetailMtId(Long seqRootUserRoleDetailMtId) {
        this.seqRootUserRoleDetailMtId = seqRootUserRoleDetailMtId;
    }

    /**
     * 削除フラグを返す
     * 
     * @return deleteFlag
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 削除フラグを設定する
     * 
     * @param deleteFlag
     *     削除フラグ
     */
    public void setDeleteFlag(Boolean deleteFlag) {
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
     * 登録日時を返す
     *
     * @return passwordExpire
     */
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * 登録日時を設定する
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

    /**
     * ユーザ権限を返す
     *
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * ユーザ権限を設定する
     *
     * @param role
     *     ユーザ権限
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * ユーザ権限名を返す
     *
     * @return roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * ユーザ権限名を設定する
     *
     * @param roleName
     *     ユーザ権限名
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 登録日時を返す
     *
     * @return regDate
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * 登録日時を設定する
     *
     * @param regDate
     *     登録日時
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * 更新日時を返す
     *
     * @return updateDate
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * 更新日時を設定する
     *
     * @param updateDate
     *     更新日時
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

}
