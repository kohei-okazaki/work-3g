package jp.co.ha.business.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 健康情報照会DTO
 *
 * @version 1.0.0
 */
public class HealthInfoReferenceDto {

    /* request */
    /** 健康情報ID */
    private Long seqHealthInfoId;
    /** 健康情報作成日直接指定フラグ */
    private String healthInfoRegDateSelectFlag;
    /** 健康情報作成日(開始) */
    private LocalDate fromHealthInfoRegDate;
    /** 健康情報作成日(終了) */
    private LocalDate toHealthInfoRegDate;

    /* response */
    /** 身長 */
    private BigDecimal height;
    /** 体重 */
    private BigDecimal weight;
    /** BMI */
    private BigDecimal bmi;
    /** 標準体重 */
    private BigDecimal standardWeight;
    /** 健康情報作成日時 */
    private String healthInfoRegDate;

    /**
     * 健康情報IDを返す
     *
     * @return seqHealthInfoId
     */
    public Long getSeqHealthInfoId() {
        return seqHealthInfoId;
    }

    /**
     * 健康情報IDを設定する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     */
    public void setSeqHealthInfoId(Long seqHealthInfoId) {
        this.seqHealthInfoId = seqHealthInfoId;
    }

    /**
     * 健康情報作成日直接指定フラグを返す
     *
     * @return healthInfoRegDateSelectFlag
     */
    public String getHealthInfoRegDateSelectFlag() {
        return healthInfoRegDateSelectFlag;
    }

    /**
     * 健康情報作成日直接指定フラグを設定する
     *
     * @param healthInfoRegDateSelectFlag
     *     健康情報作成日直接指定フラグ
     */
    public void setHealthInfoRegDateSelectFlag(String healthInfoRegDateSelectFlag) {
        this.healthInfoRegDateSelectFlag = healthInfoRegDateSelectFlag;
    }

    /**
     * 健康情報作成日(開始)を返す
     *
     * @return fromHealthInfoRegDate
     */
    public LocalDate getFromHealthInfoRegDate() {
        return fromHealthInfoRegDate;
    }

    /**
     * 健康情報作成日(開始)を設定する
     *
     * @param fromHealthInfoRegDate
     *     健康情報作成日(開始)
     */
    public void setFromHealthInfoRegDate(LocalDate fromHealthInfoRegDate) {
        this.fromHealthInfoRegDate = fromHealthInfoRegDate;
    }

    /**
     * 健康情報作成日(終了)を返す
     *
     * @return toHealthInfoRegDate
     */
    public LocalDate getToHealthInfoRegDate() {
        return toHealthInfoRegDate;
    }

    /**
     * 健康情報作成日(終了)を設定する
     *
     * @param toHealthInfoRegDate
     *     健康情報作成日(終了)
     */
    public void setToHealthInfoRegDate(LocalDate toHealthInfoRegDate) {
        this.toHealthInfoRegDate = toHealthInfoRegDate;
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
     * BMIを返す
     *
     * @return bmi
     */
    public BigDecimal getBmi() {
        return bmi;
    }

    /**
     * BMIを設定する
     *
     * @param bmi
     *     BMI
     */
    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    /**
     * 標準体重を返す
     *
     * @return standardWeight
     */
    public BigDecimal getStandardWeight() {
        return standardWeight;
    }

    /**
     * 標準体重を設定する
     *
     * @param standardWeight
     *     標準体重
     */
    public void setStandardWeight(BigDecimal standardWeight) {
        this.standardWeight = standardWeight;
    }

    /**
     * 健康情報作成日時を返す
     *
     * @return healthInfoRegDate
     */
    public String getHealthInfoRegDate() {
        return healthInfoRegDate;
    }

    /**
     * 健康情報作成日時を設定する
     *
     * @param healthInfoRegDate
     *     健康情報作成日時
     */
    public void setHealthInfoRegDate(String healthInfoRegDate) {
        this.healthInfoRegDate = healthInfoRegDate;
    }

}
