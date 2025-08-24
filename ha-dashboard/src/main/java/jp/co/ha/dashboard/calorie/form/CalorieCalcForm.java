package jp.co.ha.dashboard.calorie.form;

import java.math.BigDecimal;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * カロリー計算画面フォーム
 *
 * @version 1.0.0
 */
public class CalorieCalcForm implements BaseForm {

    /** 年齢 */
    @Required(message = "年齢が未入力です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "年齢は半角数字で入力して下さい")
    @Decimal(min = "0", max = "150", message = "年齢は0~150で入力してください")
    private Integer age;
    /** 性別 */
    private String gender;
    /** 身長 */
    @Mask
    @Required(message = "身長が未入力です")
    @Decimal(min = "0", max = "300", message = "身長は0~300で入力してください")
    private BigDecimal height;
    /** 体重 */
    @Mask
    @Required(message = "体重が未入力です")
    @Decimal(min = "0", max = "300", message = "体重は0~300で入力してください")
    private BigDecimal weight;
    /** 生活活動代謝 */
    @Required(message = "生活活動代謝が未入力です")
    @Pattern(regixPattern = RegexType.DECIMAL, message = "生活活動代謝は数字で入力して下さい")
    @Decimal(min = "1", max = "10000", message = "生活活動代謝は0~10000で入力してください")
    private BigDecimal lifeWorkMetabolism;

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

    /**
     * 体重を返す
     *
     * @return weight
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * 体重を設定する
     *
     * @param weight
     *     体重
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * 生活活動代謝を返す
     *
     * @return lifeWorkMetabolism
     */
    public BigDecimal getLifeWorkMetabolism() {
        return lifeWorkMetabolism;
    }

    /**
     * 生活活動代謝を設定する
     *
     * @param lifeWorkMetabolism
     *     生活活動代謝
     */
    public void setLifeWorkMetabolism(BigDecimal lifeWorkMetabolism) {
        this.lifeWorkMetabolism = lifeWorkMetabolism;
    }

}
