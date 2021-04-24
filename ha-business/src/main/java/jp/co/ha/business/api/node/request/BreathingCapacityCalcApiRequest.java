package jp.co.ha.business.api.node.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.web.form.BaseApiRequest;

/**
 * 肺活量計算APIリクエストクラス
 *
 * @version 1.0.0
 */
public class BreathingCapacityCalcApiRequest extends BaseNodeApiRequest
        implements BaseApiRequest {

    /** 年齢 */
    @JsonProperty("age")
    private Integer age;
    /** 性別 */
    @JsonProperty("gender")
    private GenderType genderType;
    /** 身長 */
    @JsonProperty("height")
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

}
