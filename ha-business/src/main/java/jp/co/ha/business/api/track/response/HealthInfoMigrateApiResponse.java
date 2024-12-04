package jp.co.ha.business.api.track.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;

/**
 * 健康情報連携APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class HealthInfoMigrateApiResponse extends BaseTrackApiResponse
        implements BaseApiResponse {

    /** id */
    @JsonProperty("id")
    private Long id;
    /** ユーザID */
    @JsonProperty("seq_user_id")
    private String seqUserId;
    /** 連携日時 */
    @JsonProperty("synced_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
    private LocalDateTime syncedAt;
    /** 登録日時 */
    @JsonProperty("created_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
    private LocalDateTime createdAt;
    /** 健康情報 */
    @JsonProperty("health_info")
    private HealthInfo healthInfo;

    /**
     * idを返す
     * 
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * idを設定する
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     */
    public void setSeqUserId(String seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * syncedAtを返す
     * 
     * @return syncedAt
     */
    public LocalDateTime getSyncedAt() {
        return syncedAt;
    }

    /**
     * syncedAtを設定する
     * 
     * @param syncedAt
     */
    public void setSyncedAt(LocalDateTime syncedAt) {
        this.syncedAt = syncedAt;
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
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HealthInfo {

        /** 健康情報ID */
        @JsonProperty("seq_health_info_id")
        private String seqHealthInfoId;
        /** 身長 */
        @JsonProperty("height")
        private BigDecimal height;
        /** 体重 */
        @JsonProperty("weight")
        private BigDecimal weight;
        /** BMI */
        @JsonProperty("bmi")
        private BigDecimal bmi;
        /** 標準体重 */
        @JsonProperty("standard_weight")
        private BigDecimal standardWeight;
        /** 登録日時 */
        @JsonProperty("created_at")
        private String createdAt;

        /**
         * seqHealthInfoIdを返す
         *
         * @return seqHealthInfoId
         */
        public String getSeqHealthInfoId() {
            return seqHealthInfoId;
        }

        /**
         * seqHealthInfoIdを設定する
         *
         * @param seqHealthInfoId
         */
        public void setSeqHealthInfoId(String seqHealthInfoId) {
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
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         * createdAtを設定する
         *
         * @param createdAt
         */
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }

}
