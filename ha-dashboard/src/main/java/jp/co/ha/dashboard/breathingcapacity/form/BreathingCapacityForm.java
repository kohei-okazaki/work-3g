package jp.co.ha.dashboard.breathingcapacity.form;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * 肺活量計算画面フォーム
 *
 * @version 1.0.0
 */
public class BreathingCapacityForm implements BaseForm {

    /** 年齢 */
    @Required(message = "年齢が未入力です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "年齢は半角数字で入力して下さい")
    private Integer age;
    /** 性別 */
    @Required(message = "性別が未入力です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "性別の値が不正です")
    private String gender;
    /** 身長 */
    @Mask
    @Required(message = "身長が未入力です")
    @DecimalMin(value = "1", message = "身長は1以上の値を入力してください")
    @DecimalMax(value = "999", message = "身長が桁数超過です")
    private BigDecimal height;

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
     * genderを返す
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * genderを設定する
     *
     * @param gender
     *     性別
     */
    public void setGender(String gender) {
        this.gender = gender;
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

}
