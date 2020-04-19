package jp.co.ha.business.dto;

import java.math.BigDecimal;

/**
 * 健康情報照会詳細DTO
 *
 * @version 1.0.0
 */
public class HealthInfoRefDetailDto {

    /** 健康情報ID */
    private Integer seqHealthInfoId;
    /** ユーザID */
    private String userId;
    /** 身長 */
    private BigDecimal height;
    /** 体重 */
    private BigDecimal weight;
    /** BMI */
    private BigDecimal bmi;
    /** 標準体重 */
    private BigDecimal standardWeight;
    /** 健康情報ステータスメッセージ */
    private String healthInfoStatusMessage;
    /** 健康情報登録日時 */
    private String healthInfoRegDate;
    /** BMI範囲ID */
    private Integer seqBmiRangeId;
    /** 肥満度メッセージ */
    private String overweightMessage;

    /**
     * seqHealthInfoIdを返す
     *
     * @return seqHealthInfoId
     */
    public Integer getSeqHealthInfoId() {
        return seqHealthInfoId;
    }

    /**
     * seqHealthInfoIdを設定する
     *
     * @param seqHealthInfoId
     *     健康情報ID
     */
    public void setSeqHealthInfoId(Integer seqHealthInfoId) {
        this.seqHealthInfoId = seqHealthInfoId;
    }

    /**
     * userIdを返す
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userIdを設定する
     *
     * @param userId
     *     ユーザID
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * healthInfoStatusMessageを返す
     *
     * @return healthInfoStatusMessage
     */
    public String getHealthInfoStatusMessage() {
        return healthInfoStatusMessage;
    }

    /**
     * healthInfoStatusMessageを設定する
     *
     * @param healthInfoStatusMessage
     *     健康情報ステータスメッセージ
     */
    public void setHealthInfoStatusMessage(String healthInfoStatusMessage) {
        this.healthInfoStatusMessage = healthInfoStatusMessage;
    }

    /**
     * healthInfoRegDateを返す
     *
     * @return healthInfoRegDate
     */
    public String getHealthInfoRegDate() {
        return healthInfoRegDate;
    }

    /**
     * healthInfoRegDateを設定する
     *
     * @param healthInfoRegDate
     *     健康情報登録日時
     */
    public void setHealthInfoRegDate(String healthInfoRegDate) {
        this.healthInfoRegDate = healthInfoRegDate;
    }

    /**
     * seqBmiRangeIdを返す
     *
     * @return seqBmiRangeId
     */
    public Integer getSeqBmiRangeId() {
        return seqBmiRangeId;
    }

    /**
     * seqBmiRangeIdを設定する
     *
     * @param seqBmiRangeId
     *     BMI範囲ID
     */
    public void setSeqBmiRangeId(Integer seqBmiRangeId) {
        this.seqBmiRangeId = seqBmiRangeId;
    }

    /**
     * overweightMessageを返す
     *
     * @return overweightMessage
     */
    public String getOverweightMessage() {
        return overweightMessage;
    }

    /**
     * overweightMessageを設定する
     *
     * @param overweightMessage
     *     肥満度メッセージ
     */
    public void setOverweightMessage(String overweightMessage) {
        this.overweightMessage = overweightMessage;
    }

}
