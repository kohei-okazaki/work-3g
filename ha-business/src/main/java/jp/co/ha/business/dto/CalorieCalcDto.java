package jp.co.ha.business.dto;

import java.math.BigDecimal;

import jp.co.ha.business.healthInfo.type.GenderType;

/**
 * カロリー計算DTO
 *
 * @version 1.0.0
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
     * @return genderType
     */
    public GenderType getGenderType() {
        return genderType;
    }

    /**
     * 性別を設定する
     *
     * @param genderType
     *     性別
     */
    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
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

    /**
     * 基礎代謝量を返す
     *
     * @return baseMetabolism
     */
    public BigDecimal getBaseMetabolism() {
        return baseMetabolism;
    }

    /**
     * 基礎代謝量を設定する
     *
     * @param baseMetabolism
     *     基礎代謝量
     */
    public void setBaseMetabolism(BigDecimal baseMetabolism) {
        this.baseMetabolism = baseMetabolism;
    }

    /**
     * 1日の消費カロリーを返す
     *
     * @return lostCaloriePerDay
     */
    public BigDecimal getLostCaloriePerDay() {
        return lostCaloriePerDay;
    }

    /**
     * 1日の消費カロリーを設定する
     *
     * @param lostCaloriePerDay
     *     1日の消費カロリー
     */
    public void setLostCaloriePerDay(BigDecimal lostCaloriePerDay) {
        this.lostCaloriePerDay = lostCaloriePerDay;
    }

}
