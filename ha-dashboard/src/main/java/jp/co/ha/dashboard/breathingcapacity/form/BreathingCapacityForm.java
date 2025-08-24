package jp.co.ha.dashboard.breathingcapacity.form;

import java.math.BigDecimal;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Decimal;
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
    private String gender;
    /** 身長 */
    @Mask
    @Required(message = "身長が未入力です")
    @Decimal(min = "1", max = "300", message = "身長が不正な値です")
    private BigDecimal height;

    /**
     * 年齢を返す
     *
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 年齢を設定する
     *
     * @param age
     *     年齢
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 性別を返す
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 性別を設定する
     *
     * @param gender
     *     性別
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 身長を返す
     *
     * @return height
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * 身長を設定する
     *
     * @param height
     *     身長
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

}
