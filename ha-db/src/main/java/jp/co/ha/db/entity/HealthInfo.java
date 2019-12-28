package jp.co.ha.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報Entity
 *
 * @since 1.0
 */
@Entity
public class HealthInfo implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.HEALTH_INFO_ID
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	private Integer healthInfoId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.USER_ID
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	private String userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.HEIGHT
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	@Mask
	private BigDecimal height;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.WEIGHT
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	@Mask
	private BigDecimal weight;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.BMI
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	@Mask
	private BigDecimal bmi;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.STANDARD_WEIGHT
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	@Mask
	private BigDecimal standardWeight;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.HEALTH_INFO_STATUS
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	private String healthInfoStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.HEALTH_INFO_REG_DATE
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	private Date healthInfoRegDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.UPDATE_DATE
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	private Date updateDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column health_info.REG_DATE
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	private Date regDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database table health_info
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	@Ignore
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.HEALTH_INFO_ID
	 *
	 * @return the value of health_info.HEALTH_INFO_ID
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public Integer getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.HEALTH_INFO_ID
	 *
	 * @param healthInfoId
	 *     the value for health_info.HEALTH_INFO_ID
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.USER_ID
	 *
	 * @return the value of health_info.USER_ID
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.USER_ID
	 *
	 * @param userId
	 *     the value for health_info.USER_ID
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.HEIGHT
	 *
	 * @return the value of health_info.HEIGHT
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.HEIGHT
	 *
	 * @param height
	 *     the value for health_info.HEIGHT
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.WEIGHT
	 *
	 * @return the value of health_info.WEIGHT
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.WEIGHT
	 *
	 * @param weight
	 *     the value for health_info.WEIGHT
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.BMI
	 *
	 * @return the value of health_info.BMI
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.BMI
	 *
	 * @param bmi
	 *     the value for health_info.BMI
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.STANDARD_WEIGHT
	 *
	 * @return the value of health_info.STANDARD_WEIGHT
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.STANDARD_WEIGHT
	 *
	 * @param standardWeight
	 *     the value for health_info.STANDARD_WEIGHT
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.HEALTH_INFO_STATUS
	 *
	 * @return the value of health_info.HEALTH_INFO_STATUS
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public String getHealthInfoStatus() {
		return healthInfoStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.HEALTH_INFO_STATUS
	 *
	 * @param healthInfoStatus
	 *     the value for health_info.HEALTH_INFO_STATUS
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setHealthInfoStatus(String healthInfoStatus) {
		this.healthInfoStatus = healthInfoStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.HEALTH_INFO_REG_DATE
	 *
	 * @return the value of health_info.HEALTH_INFO_REG_DATE
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public Date getHealthInfoRegDate() {
		return healthInfoRegDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.HEALTH_INFO_REG_DATE
	 *
	 * @param healthInfoRegDate
	 *     the value for health_info.HEALTH_INFO_REG_DATE
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setHealthInfoRegDate(Date healthInfoRegDate) {
		this.healthInfoRegDate = healthInfoRegDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.UPDATE_DATE
	 *
	 * @return the value of health_info.UPDATE_DATE
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.UPDATE_DATE
	 *
	 * @param updateDate
	 *     the value for health_info.UPDATE_DATE
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column health_info.REG_DATE
	 *
	 * @return the value of health_info.REG_DATE
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column health_info.REG_DATE
	 *
	 * @param regDate
	 *     the value for health_info.REG_DATE
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table health_info
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", healthInfoId=").append(healthInfoId);
		sb.append(", userId=").append(userId);
		sb.append(", height=").append(height);
		sb.append(", weight=").append(weight);
		sb.append(", bmi=").append(bmi);
		sb.append(", standardWeight=").append(standardWeight);
		sb.append(", healthInfoStatus=").append(healthInfoStatus);
		sb.append(", healthInfoRegDate=").append(healthInfoRegDate);
		sb.append(", updateDate=").append(updateDate);
		sb.append(", regDate=").append(regDate);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table health_info
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
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
		HealthInfo other = (HealthInfo) that;
		return (this.getHealthInfoId() == null ? other.getHealthInfoId() == null
				: this.getHealthInfoId().equals(other.getHealthInfoId()))
				&& (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
				&& (this.getHeight() == null ? other.getHeight() == null : this.getHeight().equals(other.getHeight()))
				&& (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
				&& (this.getBmi() == null ? other.getBmi() == null : this.getBmi().equals(other.getBmi()))
				&& (this.getStandardWeight() == null ? other.getStandardWeight() == null
						: this.getStandardWeight().equals(other.getStandardWeight()))
				&& (this.getHealthInfoStatus() == null ? other.getHealthInfoStatus() == null
						: this.getHealthInfoStatus().equals(other.getHealthInfoStatus()))
				&& (this.getHealthInfoRegDate() == null ? other.getHealthInfoRegDate() == null
						: this.getHealthInfoRegDate().equals(other.getHealthInfoRegDate()))
				&& (this.getUpdateDate() == null ? other.getUpdateDate() == null
						: this.getUpdateDate().equals(other.getUpdateDate()))
				&& (this.getRegDate() == null ? other.getRegDate() == null
						: this.getRegDate().equals(other.getRegDate()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table health_info
	 *
	 * @mbg.generated Sat Dec 28 09:17:47 GMT+09:00 2019
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getHealthInfoId() == null) ? 0 : getHealthInfoId().hashCode());
		result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime * result + ((getHeight() == null) ? 0 : getHeight().hashCode());
		result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
		result = prime * result + ((getBmi() == null) ? 0 : getBmi().hashCode());
		result = prime * result + ((getStandardWeight() == null) ? 0 : getStandardWeight().hashCode());
		result = prime * result + ((getHealthInfoStatus() == null) ? 0 : getHealthInfoStatus().hashCode());
		result = prime * result + ((getHealthInfoRegDate() == null) ? 0 : getHealthInfoRegDate().hashCode());
		result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
		result = prime * result + ((getRegDate() == null) ? 0 : getRegDate().hashCode());
		return result;
	}
}