package jp.co.ha.business.api.healthinfoapp.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.BaseRestApiResponse;

/**
 * 健康情報登録レスポンスクラス
 *
 * @version 1.0.0
 */
public class HealthInfoRegistApiResponse extends BaseRestApiResponse
        implements BaseApiResponse {

    /** 健康情報 */
    @JsonProperty("healthInfo")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
     * 健康情報登録API-healthinfo
     *
     * @version 1.0.0
     */
    public static class HealthInfo {

        /** 健康情報ID */
        @JsonProperty("seqHealthInfoId")
        private Long seqHealthInfoId;
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
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Tokyo")
        private LocalDateTime healthInfoRegDate;

        /**
         * seqHealthInfoIdを返す
         *
         * @return seqHealthInfoId
         */
        public Long getSeqHealthInfoId() {
            return seqHealthInfoId;
        }

        /**
         * seqHealthInfoIdを設定する
         *
         * @param seqHealthInfoId
         *     健康情報ID
         */
        public void setSeqHealthInfoId(Long seqHealthInfoId) {
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
        public LocalDateTime getHealthInfoRegDate() {
            return healthInfoRegDate;
        }

        /**
         * healthInfoRegDateを設定する
         *
         * @param healthInfoRegDate
         *     健康情報作成日時
         */
        public void setHealthInfoRegDate(LocalDateTime healthInfoRegDate) {
            this.healthInfoRegDate = healthInfoRegDate;
        }
    }

}
