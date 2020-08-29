package jp.co.ha.business.dto;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.GenderType;

/**
 * 肺活量計算DTO
 *
 * @version 1.0.0
 */
public class BreathingCapacityDto {

    /** 年齢 */
    private Integer age;
    /** 性別 */
    private GenderType genderType;
    /** 身長 */
    private BigDecimal height;
    /** 予測肺活量 */
    private BigDecimal predictBreathingCapacity;
    /** 肺活量% */
    private BigDecimal breathingCapacityPercentage;

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
     * predictBreathingCapacityを返す
     *
     * @return predictBreathingCapacity
     */
    public BigDecimal getPredictBreathingCapacity() {
        return predictBreathingCapacity;
    }

    /**
     * predictBreathingCapacityを設定する
     *
     * @param predictBreathingCapacity
     *     予測肺活量
     */
    public void setPredictBreathingCapacity(BigDecimal predictBreathingCapacity) {
        this.predictBreathingCapacity = predictBreathingCapacity;
    }

    /**
     * breathingCapacityPercentageを返す
     *
     * @return breathingCapacityPercentage
     */
    public BigDecimal getBreathingCapacityPercentage() {
        return breathingCapacityPercentage;
    }

    /**
     * breathingCapacityPercentageを設定する
     *
     * @param breathingCapacityPercentage
     *     肺活量%
     */
    public void setBreathingCapacityPercentage(BigDecimal breathingCapacityPercentage) {
        this.breathingCapacityPercentage = breathingCapacityPercentage;
    }

}
