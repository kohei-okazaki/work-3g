package jp.co.ha.business.dto;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.GenderType;

/**
 * カロリー計算DTO
 *
 * @since 1.0
 */
public class CalorieCalcDto {

	/** 年齢 */
	private Integer age;
	/** 性別 */
	private GenderType genderType;
	/** 身長 */
	private BigDecimal height;
	/** 体重 */
	private BigDecimal weight;
	/** 生活活動代謝 */
	private BigDecimal lifeWorkMetabolism;
	/** 基礎代謝量 */
	private BigDecimal baseMetabolism;
	/** 1日の消費カロリー */
	private BigDecimal lostCaloriePerDay;

	/**
	 * ageを返す
	 *
	 * @return age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * ageを設定する
	 *
	 * @param age
	 *     年齢
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * genderTypeを返す
	 *
	 * @return genderType
	 */
	public GenderType getGenderType() {
		return genderType;
	}

	/**
	 * genderTypeを設定する
	 *
	 * @param genderType
	 *     性別
	 */
	public void setGenderType(GenderType genderType) {
		this.genderType = genderType;
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
	 *     身長
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
	 *     体重
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * lifeWorkMetabolismを返す
	 *
	 * @return lifeWorkMetabolism
	 */
	public BigDecimal getLifeWorkMetabolism() {
		return lifeWorkMetabolism;
	}

	/**
	 * lifeWorkMetabolismを設定する
	 *
	 * @param lifeWorkMetabolism
	 *     生活活動代謝
	 */
	public void setLifeWorkMetabolism(BigDecimal lifeWorkMetabolism) {
		this.lifeWorkMetabolism = lifeWorkMetabolism;
	}

	/**
	 * baseMetabolismを返す
	 *
	 * @return baseMetabolism
	 */
	public BigDecimal getBaseMetabolism() {
		return baseMetabolism;
	}

	/**
	 * baseMetabolismを設定する
	 *
	 * @param baseMetabolism
	 *     基礎代謝量
	 */
	public void setBaseMetabolism(BigDecimal baseMetabolism) {
		this.baseMetabolism = baseMetabolism;
	}

	/**
	 * lostCaloriePerDayを返す
	 *
	 * @return lostCaloriePerDay
	 */
	public BigDecimal getLostCaloriePerDay() {
		return lostCaloriePerDay;
	}

	/**
	 * lostCaloriePerDayを設定する
	 *
	 * @param lostCaloriePerDay
	 *     1日の消費カロリー
	 */
	public void setLostCaloriePerDay(BigDecimal lostCaloriePerDay) {
		this.lostCaloriePerDay = lostCaloriePerDay;
	}

}
