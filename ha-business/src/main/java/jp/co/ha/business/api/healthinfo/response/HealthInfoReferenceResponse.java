package jp.co.ha.business.api.healthinfo.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.web.form.BaseRestApiResponse;

/**
 * 健康情報照会レスポンスクラス
 *
 * @version 1.0.0
 */
public class HealthInfoReferenceResponse extends BaseRestApiResponse {

    /** 健康情報 */
    @JsonProperty("healthInfo")
    private HealthInfo healthInfo;

    /**
     * healthInfoを返す
     *
     * @return healthInfo
     */
    public HealthInfo getHealthInfo() {
        return healthInfo;
    }

    /**
     * healthInfoを設定する
     *
     * @param healthInfo
     *     健康情報
     */
    public void setHealthInfo(HealthInfo healthInfo) {
        this.healthInfo = healthInfo;
    }

    /**
     * 健康情報照会API-healthinfo
     *
     * @version 1.0.0
     */
    public static class HealthInfo {

        /** 健康情報ID */
        @JsonProperty("seqHealthInfoId")
        private Integer seqHealthInfoId;
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
        /** 健康情報ステータス */
        @JsonProperty("status")
        private String healthInfoStatus;
        /** 健康情報作成日時 */
        @JsonProperty("regDate")
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

}
