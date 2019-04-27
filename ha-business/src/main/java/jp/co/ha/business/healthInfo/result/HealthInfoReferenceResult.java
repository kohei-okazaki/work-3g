package jp.co.ha.business.healthInfo.result;

import java.math.BigDecimal;

import jp.co.ha.web.form.BaseForm;

/**
 * 健康情報照会結果フォームクラス
 *
 */
public class HealthInfoReferenceResult implements BaseForm {

	/** シリアルバージョンUID */
	private static final long serialVersionUID = 1L;

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
	 *     BMI
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
	 *     標準体重
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
	 *     健康情報作成日時
	 */
	public void setHealthInfoRegDate(String healthInfoRegDate) {
		this.healthInfoRegDate = healthInfoRegDate;
	}

}
