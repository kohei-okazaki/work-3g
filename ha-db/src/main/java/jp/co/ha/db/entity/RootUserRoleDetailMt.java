package jp.co.ha.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;

/**
 * 管理者サイトユーザ権限詳細マスタ
 *
 * @version 1.0.0
 */
@Entity
public class RootUserRoleDetailMt extends RootUserRoleDetailMtKey
        implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_user_role_detail_mt.SEQ_ROOT_USER_ROLE_MT_ID
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    private Integer seqRootUserRoleMtId;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_user_role_detail_mt.SEQ_ROOT_ROLE_MT_ID
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    private Integer seqRootRoleMtId;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_user_role_detail_mt.UPDATE_DATE
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    private LocalDateTime updateDate;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_user_role_detail_mt.REG_DATE
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    private LocalDateTime regDate;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table root_user_role_detail_mt
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    @Ignore
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column
     * root_user_role_detail_mt.SEQ_ROOT_USER_ROLE_MT_ID
     *
     * @return the value of root_user_role_detail_mt.SEQ_ROOT_USER_ROLE_MT_ID
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public Integer getSeqRootUserRoleMtId() {
        return seqRootUserRoleMtId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column
     * root_user_role_detail_mt.SEQ_ROOT_USER_ROLE_MT_ID
     *
     * @param seqRootUserRoleMtId
     *     the value for root_user_role_detail_mt.SEQ_ROOT_USER_ROLE_MT_ID
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public void setSeqRootUserRoleMtId(Integer seqRootUserRoleMtId) {
        this.seqRootUserRoleMtId = seqRootUserRoleMtId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_user_role_detail_mt.SEQ_ROOT_ROLE_MT_ID
     *
     * @return the value of root_user_role_detail_mt.SEQ_ROOT_ROLE_MT_ID
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public Integer getSeqRootRoleMtId() {
        return seqRootRoleMtId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_user_role_detail_mt.SEQ_ROOT_ROLE_MT_ID
     *
     * @param seqRootRoleMtId
     *     the value for root_user_role_detail_mt.SEQ_ROOT_ROLE_MT_ID
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public void setSeqRootRoleMtId(Integer seqRootRoleMtId) {
        this.seqRootRoleMtId = seqRootRoleMtId;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_user_role_detail_mt.UPDATE_DATE
     *
     * @return the value of root_user_role_detail_mt.UPDATE_DATE
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_user_role_detail_mt.UPDATE_DATE
     *
     * @param updateDate
     *     the value for root_user_role_detail_mt.UPDATE_DATE
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_user_role_detail_mt.REG_DATE
     *
     * @return the value of root_user_role_detail_mt.REG_DATE
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_user_role_detail_mt.REG_DATE
     *
     * @param regDate
     *     the value for root_user_role_detail_mt.REG_DATE
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seqRootUserRoleMtId=").append(seqRootUserRoleMtId);
        sb.append(", seqRootRoleMtId=").append(seqRootRoleMtId);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", regDate=").append(regDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
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
        RootUserRoleDetailMt other = (RootUserRoleDetailMt) that;
        return (this.getSeqRootUserRoleDetailMtId() == null
                ? other.getSeqRootUserRoleDetailMtId() == null
                : this.getSeqRootUserRoleDetailMtId()
                        .equals(other.getSeqRootUserRoleDetailMtId()))
                && (this.getSeqRootUserRoleMtId() == null
                        ? other.getSeqRootUserRoleMtId() == null
                        : this.getSeqRootUserRoleMtId()
                                .equals(other.getSeqRootUserRoleMtId()))
                && (this.getSeqRootRoleMtId() == null ? other.getSeqRootRoleMtId() == null
                        : this.getSeqRootRoleMtId().equals(other.getSeqRootRoleMtId()))
                && (this.getUpdateDate() == null ? other.getUpdateDate() == null
                        : this.getUpdateDate().equals(other.getUpdateDate()))
                && (this.getRegDate() == null ? other.getRegDate() == null
                        : this.getRegDate().equals(other.getRegDate()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_user_role_detail_mt
     *
     * @mbg.generated Thu Feb 11 20:04:01 GMT+09:00 2021
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqRootUserRoleDetailMtId() == null) ? 0
                : getSeqRootUserRoleDetailMtId().hashCode());
        result = prime * result + ((getSeqRootUserRoleMtId() == null) ? 0
                : getSeqRootUserRoleMtId().hashCode());
        result = prime * result
                + ((getSeqRootRoleMtId() == null) ? 0 : getSeqRootRoleMtId().hashCode());
        result = prime * result
                + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getRegDate() == null) ? 0 : getRegDate().hashCode());
        return result;
    }
}