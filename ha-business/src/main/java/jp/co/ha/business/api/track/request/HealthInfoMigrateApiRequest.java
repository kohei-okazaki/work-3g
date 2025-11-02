package jp.co.ha.business.api.track.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    /** 健康情報リスト */
    @JsonProperty("health_infos")
    private List<HealthInfo> healthInfoList;

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
     * 健康情報リストを返す
     * 
     * @return healthInfoList
     */
    public List<HealthInfo> getHealthInfoList() {
        return healthInfoList;
    }

    /**
     * 健康情報リストを設定する
     * 
     * @param healthInfoList
     *     健康情報リスト
     */
    public void setHealthInfoList(List<HealthInfo> healthInfoList) {
        this.healthInfoList = healthInfoList;
    }

    /**
     * 健康情報
     * 
     * @version 1.0.0
     */
    public static class HealthInfo {

        /** 健康情報ID */
        @JsonProperty("seq_health_info_id")
        private Long seqHealthInfoId;
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
        /** 作成日時 */
        @JsonProperty("created_at")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime createdAt;

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
         * 作成日時を返す
         * 
         * @return createdAt
         */
        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        /**
         * 作成日時を設定する
         * 
         * @param createdAt
         *     作成日時
         */
        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

    }

}
