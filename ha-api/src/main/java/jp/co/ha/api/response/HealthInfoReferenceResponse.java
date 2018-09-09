package jp.co.ha.api.response;

import java.math.BigDecimal;

import jp.co.ha.common.api.BaseResponse;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報照会レスポンスクラス<br>
 *
 */
public class HealthInfoReferenceResponse extends BaseResponse {

	/** 健康情報ID */
	private Integer healthInfoId;
	/** ユーザID */
	private String userId;
	/** 身長 */
	@Mask
	private BigDecimal height;
	/** 体重 */
	@Mask
	private BigDecimal weight;
	/** BMI */
	@Mask
	private BigDecimal bmi;
	/** 標準体重 */
	@Mask
	private BigDecimal standardWeight;
	/** 登録日時 */
	private String regDate;

	/**
	 * healthInfoIdを返す<br>
	 *
	 * @return healthInfoId 健康情報ID
	 */
	public Integer getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する<br>
	 *
	 * @param healthInfoId
	 *     健康情報ID
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	/**
	 * userIdを返す<br>
	 *
	 * @return userId ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する<br>
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * heightを返す<br>
	 *
	 * @return height 身長
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * heightを設定する<br>
	 *
	 * @param height
	 *     身長
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * weightを返す<br>
	 *
	 * @return weight 体重
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * weightを設定する<br>
	 *
	 * @param weight
	 *     体重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * bmiを返す<br>
	 *
	 * @return bmi BMI
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する<br>
	 *
	 * @param bmi
	 *     BMI
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す<br>
	 *
	 * @return standardWeight 標準体重
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する<br>
	 *
	 * @param standardWeight
	 *     標準体重
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * regDateを返す<br>
	 *
	 * @return regDate 登録日時
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * regDateを設定する<br>
	 *
	 * @param regDate
	 *     登録日時
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

}
