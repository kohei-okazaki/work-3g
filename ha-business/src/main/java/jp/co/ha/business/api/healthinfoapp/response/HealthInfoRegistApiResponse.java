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

/**
 * 健康情報登録APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class HealthInfoRegistApiResponse extends BaseAppApiResponse
        implements BaseApiResponse {

    /** 健康情報 */
    @JsonProperty("health_info")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HealthInfo healthInfo;

    /**
     * 健康情報を返す
     *
     * @return healthInfo
     */
    public HealthInfo getHealthInfo() {
        return healthInfo;
    }

    /**
     * 健康情報を設定する
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
        @JsonProperty("seq_health_info_id")
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
        @JsonProperty("standard_weight")
        private BigDecimal standardWeight;
        /** 健康情報作成日時 */
        @JsonProperty("reg_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Tokyo")
        private LocalDateTime healthInfoRegDate;

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
         * 健康情報作成日時を返す
         *
         * @return healthInfoRegDate
         */
        public LocalDateTime getHealthInfoRegDate() {
            return healthInfoRegDate;
        }

        /**
         * 健康情報作成日時を設定する
         *
         * @param healthInfoRegDate
         *     健康情報作成日時
         */
        public void setHealthInfoRegDate(LocalDateTime healthInfoRegDate) {
            this.healthInfoRegDate = healthInfoRegDate;
        }
    }

}
