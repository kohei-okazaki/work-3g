package jp.co.ha.business.api.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * 健康情報照会レスポンスクラス
 *
 * @version 1.0.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class HealthInfoReferenceResponse extends BaseApiResponse {

    /** 健康情報ID */
    @JsonProperty("seqHealthInfoId")
    private Integer seqHealthInfoId;
    /** ユーザID */
    @JsonProperty("userId")
    private String userId;
    /** 身長 */
    @Mask
    @JsonProperty("height")
    private BigDecimal height;
    /** 体重 */
    @Mask
    @JsonProperty("weight")
    private BigDecimal weight;
    /** BMI */
    @Mask
    @JsonProperty("bmi")
    private BigDecimal bmi;
    /** 標準体重 */
    @Mask
    @JsonProperty("standardWeight")
    private BigDecimal standardWeight;
    /** 健康情報作成日時 */
    @JsonProperty("healthInfoRegDate")
    @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Tokyo")
    private Date healthInfoRegDate;

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
     * healthInfoRegDateを返す
     *
     * @return healthInfoRegDate
     */
    public Date getHealthInfoRegDate() {
        return healthInfoRegDate;
    }

    /**
     * healthInfoRegDateを設定する
     *
     * @param healthInfoRegDate
     *     健康情報作成日時
     */
    public void setHealthInfoRegDate(Date healthInfoRegDate) {
        this.healthInfoRegDate = healthInfoRegDate;
    }

}
