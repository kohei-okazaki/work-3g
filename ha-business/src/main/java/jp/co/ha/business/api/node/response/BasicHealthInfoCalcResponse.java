package jp.co.ha.business.api.node.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 基礎健康情報計算APIのレスポンスクラス
 *
 * @version 1.0.0
 */
public class BasicHealthInfoCalcResponse extends BaseHealthinfoCalcResponse {

    /** 基礎健康情報 */
    @JsonProperty("basic_health_info")
    private BasicHealthInfo basicHealthInfo;

    /**
     * 基礎健康情報
     *
     * @version 1.0.0
     */
    public static class BasicHealthInfo {

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

    }

    /**
     * basicHealthInfoを返す
     *
     * @return basicHealthInfo
     */
    public BasicHealthInfo getBasicHealthInfo() {
        return basicHealthInfo;
    }

    /**
     * basicHealthInfoを設定する
     *
     * @param basicHealthInfo
     *     基礎健康情報
     */
    public void setBasicHealthInfo(BasicHealthInfo basicHealthInfo) {
        this.basicHealthInfo = basicHealthInfo;
    }

}
