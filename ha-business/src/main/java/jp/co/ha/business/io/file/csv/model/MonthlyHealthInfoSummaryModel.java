package jp.co.ha.business.io.file.csv.model;

import java.math.BigDecimal;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;
import jp.co.ha.common.log.annotation.Mask;

/**
 * 月次健康情報集計CSV Model
 *
 * @version 1.0.0
 */
public class MonthlyHealthInfoSummaryModel implements BaseCsvModel {

    /** ユーザID */
    private String seqUserId;
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
    /** 健康情報ステータス */
    private String healthInfoStatus;
    /** 健康情報登録日時 */
    private String healthInfoRegDate;
    /** BMIマスタID */
    private Integer seqBmiRangeId;
    /** 更新日時 */
    private String updateDate;
    /** 登録日時 */
    private String regDate;

    /**
     * seqUserIdを返す
     *
     * @return seqUserId
     */
    public String getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(String seqUserId) {
        this.seqUserId = seqUserId;
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
     * healthInfoStatusを返す
     *
     * @return healthInfoStatus
     */
    public String getHealthInfoStatus() {
        return healthInfoStatus;
    }

    /**
     * healthInfoStatusを設定する
     *
     * @param healthInfoStatus
     *     健康情報ステータス
     */
    public void setHealthInfoStatus(String healthInfoStatus) {
        this.healthInfoStatus = healthInfoStatus;
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
     *     BMIマスタID
     */
    public void setSeqBmiRangeId(Integer seqBmiRangeId) {
        this.seqBmiRangeId = seqBmiRangeId;
    }

    /**
     * updateDateを返す
     *
     * @return updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * updateDateを設定する
     *
     * @param updateDate
     *     更新日時
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * regDateを返す
     *
     * @return regDate
     */
    public String getRegDate() {
        return regDate;
    }

    /**
     * regDateを設定する
     *
     * @param regDate
     *     登録日時
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

}
