package jp.co.ha.business.dto;

import java.math.BigDecimal;

/**
 * 健康情報照会DTO
 *
 */
public class HealthInfoReferenceDto {

	/* request */
	/** 健康情報ID */
	private Integer healthInfoId;
	/** 健康情報作成日直接指定フラグ */
	private String healthInfoRegDateSelectFlag;
	/** 健康情報作成日(開始) */
	private String fromHealthInfoRegDate;
	/** 健康情報作成日(終了) */
	private String toHealthInfoRegDate;

	/* response */
	/** 身長 */
	private BigDecimal height;
	/** 体重 */
	private BigDecimal weight;
	/** BMI */
	private BigDecimal bmi;
	/** 標準体重 */
	private BigDecimal standardWeight;
	/** 健康情報作成日時 */
	private String healthInfoRegDate;

	/**
	 * healthInfoIdを返す
	 *
	 * @return healthInfoId
	 */
	public Integer getHealthInfoId() {
		return healthInfoId;
	}

	/**
	 * healthInfoIdを設定する
	 *
	 * @param healthInfoId
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	/**
	 * healthInfoRegDateSelectFlagを返す
	 *
	 * @return healthInfoRegDateSelectFlag
	 */
	public String getHealthInfoRegDateSelectFlag() {
		return healthInfoRegDateSelectFlag;
	}

	/**
	 * healthInfoRegDateSelectFlagを設定する
	 *
	 * @param healthInfoRegDateSelectFlag
	 */
	public void setHealthInfoRegDateSelectFlag(String healthInfoRegDateSelectFlag) {
		this.healthInfoRegDateSelectFlag = healthInfoRegDateSelectFlag;
	}

	/**
	 * fromHealthInfoRegDateを返す
	 *
	 * @return fromHealthInfoRegDate
	 */
	public String getFromHealthInfoRegDate() {
		return fromHealthInfoRegDate;
	}

	/**
	 * fromHealthInfoRegDateを設定する
	 *
	 * @param fromHealthInfoRegDate
	 */
	public void setFromHealthInfoRegDate(String fromHealthInfoRegDate) {
		this.fromHealthInfoRegDate = fromHealthInfoRegDate;
	}

	/**
	 * toHealthInfoRegDateを返す
	 *
	 * @return toHealthInfoRegDate
	 */
	public String getToHealthInfoRegDate() {
		return toHealthInfoRegDate;
	}

	/**
	 * toHealthInfoRegDateを設定する
	 *
	 * @param toHealthInfoRegDate
	 */
	public void setToHealthInfoRegDate(String toHealthInfoRegDate) {
		this.toHealthInfoRegDate = toHealthInfoRegDate;
	}

	/**
	 * heightを返す
	 * 
	 * @return height
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * heightを設定する
	 * 
	 * @param height
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * weightを返す
	 * 
	 * @return weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * weightを設定する
	 * 
	 * @param weight
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * bmiを返す
	 * 
	 * @return bmi
	 */
	public BigDecimal getBmi() {
		return bmi;
	}

	/**
	 * bmiを設定する
	 * 
	 * @param bmi
	 */
	public void setBmi(BigDecimal bmi) {
		this.bmi = bmi;
	}

	/**
	 * standardWeightを返す
	 * 
	 * @return standardWeight
	 */
	public BigDecimal getStandardWeight() {
		return standardWeight;
	}

	/**
	 * standardWeightを設定する
	 * 
	 * @param standardWeight
	 */
	public void setStandardWeight(BigDecimal standardWeight) {
		this.standardWeight = standardWeight;
	}

	/**
	 * healthInfoRegDateを返す
	 * 
	 * @return healthInfoRegDate
	 */
	public String getHealthInfoRegDate() {
		return healthInfoRegDate;
	}

	/**
	 * healthInfoRegDateを設定する
	 * 
	 * @param healthInfoRegDate
	 */
	public void setHealthInfoRegDate(String healthInfoRegDate) {
		this.healthInfoRegDate = healthInfoRegDate;
	}

}
