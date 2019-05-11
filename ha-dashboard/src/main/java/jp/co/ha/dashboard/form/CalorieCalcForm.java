package jp.co.ha.dashboard.form;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseForm;

/**
 * カロリー計算画面フォーム
 *
 */
public class CalorieCalcForm implements BaseForm {

	/** 年齢 */
	@Required(message = "年齢が未入力です")
	@Pattern(regixPattern = RegixType.HALF_NUMBER, message = "年齢は半角数字で入力して下さい")
	private BigDecimal age;
	/** 性別 */
	@Required(message = "性別が未入力です")
	private String type;
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
	/** 体型 */
	@Required(message = "体型が未入力です")
	private String bodyType;
	/** 生活活動代謝 */
	@Required(message = "生活活動代謝が未入力です")
	@Pattern(regixPattern = RegixType.DECIMAL, message = "生活活動代謝は半角数字の少数型で入力して下さい")
	private BigDecimal lifeWorkMetabolism;

	/**
	 * ageを返す
	 *
	 * @return age
	 */
	public BigDecimal getAge() {
		return age;
	}

	/**
	 * ageを設定する
	 *
	 * @param age
	 *     年齢
	 */
	public void setAge(BigDecimal age) {
		this.age = age;
	}

	/**
	 * typeを返す
	 *
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * typeを設定する
	 *
	 * @param type
	 *     性別
	 */
	public void setType(String type) {
		this.type = type;
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
	 * bodyTypeを返す
	 *
	 * @return bodyType
	 */
	public String getBodyType() {
		return bodyType;
	}

	/**
	 * bodyTypeを設定する
	 *
	 * @param bodyType
	 *     体型
	 */
	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
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

}
