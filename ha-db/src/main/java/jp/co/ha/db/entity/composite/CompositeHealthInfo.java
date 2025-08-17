package jp.co.ha.db.entity.composite;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 健康情報とBMI範囲マスタの複合Entity
 *
 * @version 1.0.0
 */
public class CompositeHealthInfo extends CompositeHealthInfoKey {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = -7954105828273613973L;

    /** 身長 */
    @Mask
    private BigDecimal height;
    /** 体重 */
    @Mask
    private BigDecimal weight;
    /** BMI */
    @Mask
    private BigDecimal bmi;
    /** 標準体重 */
    @Mask
    private BigDecimal standardWeight;
    /** 健康情報登録日時 */
    private LocalDateTime healthInfoRegDate;
    /** BMI範囲マスタID */
    private Long seqBmiRangeMtId;
    /** 範囲下限 */
    private Integer rangeMin;
    /** 範囲上限 */
    private Integer rangeMax;
    /** 肥満度ステータス */
    private String overWeightStatus;

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
    public LocalDateTime getHealthInfoRegDate() {
        return healthInfoRegDate;
    }

    /**
     * healthInfoRegDateを設定する
     *
     * @param healthInfoRegDate
     *     健康情報登録日時
     */
    public void setHealthInfoRegDate(LocalDateTime healthInfoRegDate) {
        this.healthInfoRegDate = healthInfoRegDate;
    }

    /**
     * seqBmiRangeMtIdを返す
     *
     * @return seqBmiRangeMtId
     */
    public Long getSeqBmiRangeMtId() {
        return seqBmiRangeMtId;
    }

    /**
     * seqBmiRangeMtIdを設定する
     *
     * @param seqBmiRangeMtId
     *     BMI範囲マスタID
     */
    public void setSeqBmiRangeMtId(Long seqBmiRangeMtId) {
        this.seqBmiRangeMtId = seqBmiRangeMtId;
    }

    /**
     * rangeMinを返す
     *
     * @return rangeMin
     */
    public Integer getRangeMin() {
        return rangeMin;
    }

    /**
     * rangeMinを設定する
     *
     * @param rangeMin
     *     範囲下限
     */
    public void setRangeMin(Integer rangeMin) {
        this.rangeMin = rangeMin;
    }

    /**
     * rangeMaxを返す
     *
     * @return rangeMax
     */
    public Integer getRangeMax() {
        return rangeMax;
    }

    /**
     * rangeMaxを設定する
     *
     * @param rangeMax
     *     範囲上限
     */
    public void setRangeMax(Integer rangeMax) {
        this.rangeMax = rangeMax;
    }

    /**
     * overWeightStatusを返す
     *
     * @return overWeightStatus
     */
    public String getOverWeightStatus() {
        return overWeightStatus;
    }

    /**
     * overWeightStatusを設定する
     *
     * @param overWeightStatus
     *     肥満度ステータス
     */
    public void setOverWeightStatus(String overWeightStatus) {
        this.overWeightStatus = overWeightStatus;
    }

}
