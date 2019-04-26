package jp.co.ha.dashboard.form;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * 健康情報入力画面フォームクラス
 *
 */
public class HealthInfoForm implements BaseForm {

	/** シリアルバージョンUID */
	@Ignore
	private static final long serialVersionUID = 1L;

	/** 健康情報ID */
	private Integer healthInfoId;
	/** ユーザID */
	private String userId;
	/** 身長 */
	@Mask
	@Required(message = "身長が未入力です")
	@DecimalMin(value = "1", message = "身長が桁数不足です")
	@DecimalMax(value = "999", message = "身長が桁数超過です")
	private BigDecimal height;
	/** 体重 */
	@Mask
	@Required(message = "体重が未入力です")
	@DecimalMin(value = "1", message = "体重が桁数不足です")
	@DecimalMax(value = "999", message = "体重が桁数超過です")
	private BigDecimal weight;
	/** BMI */
	@Mask
	private BigDecimal bmi;
	/** 標準体重 */
	@Mask
	private BigDecimal standardWeight;

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
	 *     健康情報ID
	 */
	public void setHealthInfoId(Integer healthInfoId) {
		this.healthInfoId = healthInfoId;
	}

	/**
	 * userIdを返す
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * userIdを設定する
	 *
	 * @param userId
	 *     ユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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

}
