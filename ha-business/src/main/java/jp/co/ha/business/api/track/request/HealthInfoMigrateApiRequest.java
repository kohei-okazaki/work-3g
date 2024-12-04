package jp.co.ha.business.api.track.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiRequest;

/**
 * 健康情報連携APIのリクエスト情報クラス
 *
 * @version 1.0.0
 */
public class HealthInfoMigrateApiRequest extends BaseTrackApiRequest
        implements BaseApiRequest {

    /** ユーザID */
    @JsonProperty("seq_user_id")
    private Long seqUserId;
    /** 健康情報 */
    @JsonProperty("health_info")
    private HealthInfo healthInfo;

    /**
     * seqUserIdを返す
     * 
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     * 
     * @param seqUserId
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
    }

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
     */
    public void setHealthInfo(HealthInfo healthInfo) {
        this.healthInfo = healthInfo;
    }

    /**
     * 健康情報
     * 
     * @version 1.0.0
     */
    public static class HealthInfo {

        /** ユーザID */
        @JsonProperty("seq_health_info_id")
        private BigDecimal seqHealthInfoId;
        /** ユーザID */
        @JsonProperty("height")
        private BigDecimal height;
        /** ユーザID */
        @JsonProperty("weight")
        private BigDecimal weight;
        /** ユーザID */
        @JsonProperty("bmi")
        private BigDecimal bmi;
        /** ユーザID */
        @JsonProperty("standard_weight")
        private BigDecimal standardWeight;
        /** ユーザID */
        @JsonProperty("created_at")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime createdAt;

        /**
         * seqHealthInfoIdを返す
         * 
         * @return seqHealthInfoId
         */
        public BigDecimal getSeqHealthInfoId() {
            return seqHealthInfoId;
        }

        /**
         * seqHealthInfoIdを設定する
         * 
         * @param seqHealthInfoId
         */
        public void setSeqHealthInfoId(BigDecimal seqHealthInfoId) {
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
         */
        public void setStandardWeight(BigDecimal standardWeight) {
            this.standardWeight = standardWeight;
        }

        /**
         * createdAtを返す
         * 
         * @return createdAt
         */
        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        /**
         * createdAtを設定する
         * 
         * @param createdAt
         */
        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

    }

}
