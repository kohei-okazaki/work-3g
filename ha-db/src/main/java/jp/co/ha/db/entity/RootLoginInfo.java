package jp.co.ha.db.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;

/**
 * 管理者サイトユーザログイン情報
 *
 * @version 1.0.0
 */
@Entity
public class RootLoginInfo extends RootLoginInfoKey implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.PASSWORD
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    private String password;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.DELETE_FLAG
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    private Boolean deleteFlag;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.PASSWORD_EXPIRE
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    private LocalDate passwordExpire;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.SEQ_ROOT_USER_ROLE_MNG_MT_ID
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    private Long seqRootUserRoleMngMtId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.REMARKS
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    private String remarks;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.UPDATE_DATE
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    private LocalDateTime updateDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.REG_DATE
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    private LocalDateTime regDate;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table root_login_info
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    @Ignore
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.PASSWORD
     * 
     * @return the value of root_login_info.PASSWORD
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.PASSWORD
     * 
     * @param password
     *     the value for root_login_info.PASSWORD
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.DELETE_FLAG
     * 
     * @return the value of root_login_info.DELETE_FLAG
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.DELETE_FLAG
     * 
     * @param deleteFlag
     *     the value for root_login_info.DELETE_FLAG
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.PASSWORD_EXPIRE
     * 
     * @return the value of root_login_info.PASSWORD_EXPIRE
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.PASSWORD_EXPIRE
     * 
     * @param passwordExpire
     *     the value for root_login_info.PASSWORD_EXPIRE
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public void setPasswordExpire(LocalDate passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.SEQ_ROOT_USER_ROLE_MNG_MT_ID
     * 
     * @return the value of root_login_info.SEQ_ROOT_USER_ROLE_MNG_MT_ID
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public Long getSeqRootUserRoleMngMtId() {
        return seqRootUserRoleMngMtId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.SEQ_ROOT_USER_ROLE_MNG_MT_ID
     * 
     * @param seqRootUserRoleMngMtId
     *     the value for root_login_info.SEQ_ROOT_USER_ROLE_MNG_MT_ID
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public void setSeqRootUserRoleMngMtId(Long seqRootUserRoleMngMtId) {
        this.seqRootUserRoleMngMtId = seqRootUserRoleMngMtId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.REMARKS
     * 
     * @return the value of root_login_info.REMARKS
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.REMARKS
     * 
     * @param remarks
     *     the value for root_login_info.REMARKS
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.UPDATE_DATE
     * 
     * @return the value of root_login_info.UPDATE_DATE
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.UPDATE_DATE
     * 
     * @param updateDate
     *     the value for root_login_info.UPDATE_DATE
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.REG_DATE
     * 
     * @return the value of root_login_info.REG_DATE
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.REG_DATE
     * 
     * @param regDate
     *     the value for root_login_info.REG_DATE
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", password=").append(password);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", passwordExpire=").append(passwordExpire);
        sb.append(", seqRootUserRoleMngMtId=").append(seqRootUserRoleMngMtId);
        sb.append(", remarks=").append(remarks);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", regDate=").append(regDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RootLoginInfo other = (RootLoginInfo) that;
        return (this.getSeqRootLoginInfoId() == null
                ? other.getSeqRootLoginInfoId() == null
                : this.getSeqRootLoginInfoId().equals(other.getSeqRootLoginInfoId()))
                && (this.getPassword() == null ? other.getPassword() == null
                        : this.getPassword().equals(other.getPassword()))
                && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null
                        : this.getDeleteFlag().equals(other.getDeleteFlag()))
                && (this.getPasswordExpire() == null ? other.getPasswordExpire() == null
                        : this.getPasswordExpire().equals(other.getPasswordExpire()))
                && (this.getSeqRootUserRoleMngMtId() == null
                        ? other.getSeqRootUserRoleMngMtId() == null
                        : this.getSeqRootUserRoleMngMtId()
                                .equals(other.getSeqRootUserRoleMngMtId()))
                && (this.getRemarks() == null ? other.getRemarks() == null
                        : this.getRemarks().equals(other.getRemarks()))
                && (this.getUpdateDate() == null ? other.getUpdateDate() == null
                        : this.getUpdateDate().equals(other.getUpdateDate()))
                && (this.getRegDate() == null ? other.getRegDate() == null
                        : this.getRegDate().equals(other.getRegDate()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     * 
     * @mbg.generated Sun Dec 15 12:08:13 GMT+09:00 2024
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqRootLoginInfoId() == null) ? 0
                : getSeqRootLoginInfoId().hashCode());
        result = prime * result
                + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result
                + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result
                + ((getPasswordExpire() == null) ? 0 : getPasswordExpire().hashCode());
        result = prime * result + ((getSeqRootUserRoleMngMtId() == null) ? 0
                : getSeqRootUserRoleMngMtId().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result
                + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getRegDate() == null) ? 0 : getRegDate().hashCode());
        return result;
    }
}