package jp.co.ha.business.api.node.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.web.form.BaseApiRequest;
import jp.co.ha.web.form.BaseNodeRequest;

/**
 * カロリー計算APIリクエストクラス
 *
 * @version 1.0.0
 */
public class CalorieCalcRequest extends BaseNodeRequest implements BaseApiRequest {

    /** 性別 */
    @JsonProperty("gender")
    private GenderType genderType;
    /** 年齢 */
    @JsonProperty("age")
    private Integer age;
    /** 身長 */
    @JsonProperty("height")
    private BigDecimal height;
    /** 体重 */
    @JsonProperty("weight")
    private BigDecimal weight;
    /** 生活活動代謝 */
    @JsonProperty("life_work_metabolism")
    private BigDecimal lifeWorkMetabolism;

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

}
