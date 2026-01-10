package jp.co.ha.business.io.file.csv.model;

import java.math.BigDecimal;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 日次健康情報データ分析連携CSV Model
 *
 * @version 1.0.0
 */
public class DailyHealthInfoCsvModel implements BaseCsvModel {

    /** 健康情報ID */
    private Long seqHealthInfoId;
    /** ユーザID */
    private Long seqUserId;
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
     * ユーザIDを返す
     *
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * ユーザIDを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
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
     * 健康情報登録日時を返す
     *
     * @return healthInfoRegDate
     */
    public String getHealthInfoRegDate() {
        return healthInfoRegDate;
    }

    /**
     * 健康情報登録日時を設定する
     *
     * @param healthInfoRegDate
     *     健康情報登録日時
     */
    public void setHealthInfoRegDate(String healthInfoRegDate) {
        this.healthInfoRegDate = healthInfoRegDate;
    }

}
