package jp.co.ha.business.api.node.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.LogMessageFactory;
import jp.co.ha.common.web.form.BaseApiResponse;

/**
 * 基礎健康情報計算APIのレスポンスクラス
 *
 * @version 1.0.0
 */
public class BasicHealthInfoCalcApiResponse extends BaseNodeApiResponse
        implements BaseApiResponse {

    /** 基礎健康情報 */
    @JsonProperty("basic_health_info")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

        @Override
        public String toString() {
            return LogMessageFactory.toString(this);
        }

    }

    /**
     * 基礎健康情報を返す
     *
     * @return basicHealthInfo
     */
    public BasicHealthInfo getBasicHealthInfo() {
        return basicHealthInfo;
    }

    /**
     * 基礎健康情報を設定する
     *
     * @param basicHealthInfo
     *     基礎健康情報
     */
    public void setBasicHealthInfo(BasicHealthInfo basicHealthInfo) {
        this.basicHealthInfo = basicHealthInfo;
    }

    @Override
    public String toString() {
        return LogMessageFactory.toString(this);
    }

}
