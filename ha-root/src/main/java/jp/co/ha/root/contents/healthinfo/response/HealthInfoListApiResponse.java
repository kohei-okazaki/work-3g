package jp.co.ha.root.contents.healthinfo.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.root.base.JsonEntity;;

/**
 * 健康情報一覧取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class HealthInfoListApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** 健康情報リスト */
    @JsonProperty("health_info_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<HealthInfoResponse> healthInfoResponseList;

    /**
     * healthInfoResponseListを返す
     *
     * @return healthInfoResponseList
     */
    public List<HealthInfoResponse> getHealthInfoResponseList() {
        return healthInfoResponseList;
    }

    /**
     * healthInfoResponseListを設定する
     *
     * @param healthInfoResponseList
     *     健康情報リスト
     */
    public void setHealthInfoResponseList(
            List<HealthInfoResponse> healthInfoResponseList) {
        this.healthInfoResponseList = healthInfoResponseList;
    }

    /**
     * 健康情報レスポンス
     *
     * @version 1.0.0
     */
    public static class HealthInfoResponse extends JsonEntity {

        /** 健康情報ID */
        @JsonProperty("seq_health_info_id")
        private Integer seqHealthInfoId;
        /** ユーザID */
        @JsonProperty("seq_user_id")
        private Integer seqUserId;
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
        /** 健康情報ステータス */
        @JsonProperty("health_info_status")
        private HealthInfoStatus healthInfoStatus;
        /** BMIステータス */
        @JsonProperty("bmi_status")
        private BmiStatus bmiStatus;
        /** 健康情報登録日時 */
        @JsonProperty("health_info_reg_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime healthInfoRegDate;

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
         * seqUserIdを返す
         *
         * @return seqUserId
         */
        public Integer getSeqUserId() {
            return seqUserId;
        }

        /**
         * seqUserIdを設定する
         *
         * @param seqUserId
         *     ユーザID
         */
        public void setSeqUserId(Integer seqUserId) {
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
        public HealthInfoStatus getHealthInfoStatus() {
            return healthInfoStatus;
        }

        /**
         * healthInfoStatusを設定する
         *
         * @param healthInfoStatus
         *     健康情報ステータス
         */
        public void setHealthInfoStatus(HealthInfoStatus healthInfoStatus) {
            this.healthInfoStatus = healthInfoStatus;
        }

        /**
         * bmiStatusを返す
         *
         * @return bmiStatus
         */
        public BmiStatus getBmiStatus() {
            return bmiStatus;
        }

        /**
         * bmiStatusを設定する
         *
         * @param bmiStatus
         *     BMIステータス
         */
        public void setBmiStatus(BmiStatus bmiStatus) {
            this.bmiStatus = bmiStatus;
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
         *     健康情報登録日時
         */
        public void setHealthInfoRegDate(LocalDateTime healthInfoRegDate) {
            this.healthInfoRegDate = healthInfoRegDate;
        }

    }

    /**
     * 健康情報ステータス
     *
     * @version 1.0.0
     */
    public static class HealthInfoStatus {

        /** ステータス */
        @JsonProperty("status")
        private String status;
        /** メッセージ */
        @JsonProperty("message")
        private String message;

        /**
         * statusを返す
         *
         * @return status
         */
        public String getStatus() {
            return status;
        }

        /**
         * statusを設定する
         *
         * @param status
         *     ステータス
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * messageを返す
         *
         * @return message
         */
        public String getMessage() {
            return message;
        }

        /**
         * messageを設定する
         *
         * @param message
         *     メッセージ
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * BMIステータス
     *
     * @version 1.0.0
     */
    public static class BmiStatus {

        /** ステータス */
        @JsonProperty("status")
        private String status;
        /** メッセージ */
        @JsonProperty("message")
        private String message;

        /**
         * statusを返す
         *
         * @return status
         */
        public String getStatus() {
            return status;
        }

        /**
         * statusを設定する
         *
         * @param status
         *     ステータス
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * messageを返す
         *
         * @return message
         */
        public String getMessage() {
            return message;
        }

        /**
         * messageを設定する
         *
         * @param message
         *     メッセージ
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }
}
