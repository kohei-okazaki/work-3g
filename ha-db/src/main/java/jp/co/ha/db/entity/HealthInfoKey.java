package jp.co.ha.db.entity;

import java.io.Serializable;

import jp.co.ha.common.log.annotation.Ignore;

/**
 * 健康情報のPrimaryKey
 *
 * @version 1.0.0
 */
public class HealthInfoKey implements Serializable {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column health_info.SEQ_HEALTH_INFO_ID
     *
     * @mbg.generated Sun Apr 05 13:39:31 GMT+09:00 2020
     */
    private Integer seqHealthInfoId;

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database table health_info
     *
     * @mbg.generated Sun Apr 05 13:39:31 GMT+09:00 2020
     */
    @Ignore
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column health_info.SEQ_HEALTH_INFO_ID
     *
     * @return the value of health_info.SEQ_HEALTH_INFO_ID
     * @mbg.generated Sun Apr 05 13:39:31 GMT+09:00 2020
     */
    public Integer getSeqHealthInfoId() {
        return seqHealthInfoId;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column health_info.SEQ_HEALTH_INFO_ID
     *
     * @param seqHealthInfoId
     *     the value for health_info.SEQ_HEALTH_INFO_ID
     * @mbg.generated Sun Apr 05 13:39:31 GMT+09:00 2020
     */
    public void setSeqHealthInfoId(Integer seqHealthInfoId) {
        this.seqHealthInfoId = seqHealthInfoId;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info
     *
     * @mbg.generated Sun Apr 05 13:39:31 GMT+09:00 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seqHealthInfoId=").append(seqHealthInfoId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info
     *
     * @mbg.generated Sun Apr 05 13:39:31 GMT+09:00 2020
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
        HealthInfoKey other = (HealthInfoKey) that;
        return (this.getSeqHealthInfoId() == null ? other.getSeqHealthInfoId() == null
                : this.getSeqHealthInfoId().equals(other.getSeqHealthInfoId()));
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds
     * to the database table health_info
     *
     * @mbg.generated Sun Apr 05 13:39:31 GMT+09:00 2020
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getSeqHealthInfoId() == null) ? 0 : getSeqHealthInfoId().hashCode());
        return result;
    }
}