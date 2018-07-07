package jp.co.ha.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報Entity<br>
 */
@Entity
@Table(name = "HEALTH_INFO")
public class HealthInfo implements Serializable {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** データID */
	@Id
	@Column(name = "HEALTH_INFO_ID", nullable = false, length = 3)
	private BigDecimal healthInfoId;

	/** ユーザID */
	@Column(name = "USER_ID", nullable = false, length = 3)
	private String userId;

	/** 身長 */
	@Mask
	@Column(name = "HEIGHT", nullable = false)
	private BigDecimal height;

	/** 体重 */
	@Mask
	@Column(name = "WEIGHT", nullable = false)
	private BigDecimal weight;

	/** BMI */
	@Mask
	@Column(name = "BMI", nullable = false)
	private BigDecimal bmi;

	/** 標準体重 */
	@Mask
	@Column(name = "STANDARD_WEIGHT", nullable = false)
	private BigDecimal standardWeight;

	/** ユーザステータス */
	@Column(name = "USER_STATUS", nullable = false)
	private String userStatus;

	/** 登録日時 */
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;

	/**
	 * healthInfoIdを返す<br>
	 *
	 * @return healthInfoId
	 */
	public BigDecimal getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する<br>
	 *
	 * @param healthInfoId
	 */
	public void setHealthInfoId(BigDecimal healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	/**
	 * userIdを返す
	 *
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *            ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * heightを返す
	 *
	 * @return height 身長
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * heightを設定する
	 *
	 * @param height
	 *            身長
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * weightを返す
	 *
	 * @return weight 体重
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * weightを設定する
	 *
	 * @param weight
	 *            体重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * bmiを返す
	 *
	 * @return bmi BMI
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する
	 *
	 * @param bmi
	 *            BMI
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す
	 *
	 * @return standardWeight 標準体重
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する
	 *
	 * @param standardWeight
	 *            標準体重
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * userStatusを返す
	 *
	 * @return userStatus ユーザステータス
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * userStatusを設定する
	 *
	 * @param userStatus
	 *            ユーザステータス
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * regDateを返す
	 *
	 * @return regDate 登録日時
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する
	 *
	 * @param regDate
	 *            登録日時
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}