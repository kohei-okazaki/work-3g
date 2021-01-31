package jp.co.ha.db.entity;

import java.io.Serializable;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * 管理者サイトユーザログイン情報のPrimaryKey
 *
 * @version 1.0.0
 */
public class RootLoginInfoKey implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column root_login_info.SEQ_ROOT_LOGIN_INFO_ID
     *
     * @mbg.generated Sun Jan 10 15:29:38 GMT+09:00 2021
     */
    private Integer seqRootLoginInfoId;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table root_login_info
     *
     * @mbg.generated Sun Jan 10 15:29:38 GMT+09:00 2021
     */
    @Ignore
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column root_login_info.SEQ_ROOT_LOGIN_INFO_ID
     *
     * @return the value of root_login_info.SEQ_ROOT_LOGIN_INFO_ID
     * @mbg.generated Sun Jan 10 15:29:38 GMT+09:00 2021
     */
    public Integer getSeqRootLoginInfoId() {
        return seqRootLoginInfoId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column root_login_info.SEQ_ROOT_LOGIN_INFO_ID
     *
     * @param seqRootLoginInfoId
     *     the value for root_login_info.SEQ_ROOT_LOGIN_INFO_ID
     * @mbg.generated Sun Jan 10 15:29:38 GMT+09:00 2021
     */
    public void setSeqRootLoginInfoId(Integer seqRootLoginInfoId) {
        this.seqRootLoginInfoId = seqRootLoginInfoId;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sun Jan 10 15:29:38 GMT+09:00 2021
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seqRootLoginInfoId=").append(seqRootLoginInfoId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sun Jan 10 15:29:38 GMT+09:00 2021
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
        RootLoginInfoKey other = (RootLoginInfoKey) that;
        return (this.getSeqRootLoginInfoId() == null
                ? other.getSeqRootLoginInfoId() == null
                : this.getSeqRootLoginInfoId().equals(other.getSeqRootLoginInfoId()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table root_login_info
     *
     * @mbg.generated Sun Jan 10 15:29:38 GMT+09:00 2021
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeqRootLoginInfoId() == null) ? 0
                : getSeqRootLoginInfoId().hashCode());
        return result;
    }
}