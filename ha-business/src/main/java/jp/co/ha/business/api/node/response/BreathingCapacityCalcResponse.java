package jp.co.ha.business.api.node.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * 肺活量計算APIリクエストクラス
 *
 * @version 1.0.0
 */
public class BreathingCapacityCalcResponse extends BaseNodeResponse
        implements BaseApiResponse {

    /** 肺活量計算結果情報 */
    @JsonProperty("breathing_capacity_calc_result")
    private BreathingCapacityCalcResult breathingCapacityCalcResult;
    /** ユーザ情報 */
    @JsonProperty("user_data")
    private UserData userData;

    /**
     * breathingCapacityCalcResultを返す
     *
     * @return breathingCapacityCalcResult
     */
    public BreathingCapacityCalcResult getBreathingCapacityCalcResult() {
        return breathingCapacityCalcResult;
    }

    /**
     * breathingCapacityCalcResultを設定する
     *
     * @param breathingCapacityCalcResult
     *     肺活量計算結果情報
     */
    public void setBreathingCapacityCalcResult(
            BreathingCapacityCalcResult breathingCapacityCalcResult) {
        this.breathingCapacityCalcResult = breathingCapacityCalcResult;
    }

    /**
     * userDataを返す
     *
     * @return userData
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * userDataを設定する
     *
     * @param userData
     *     ユーザ情報
     */
    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    /**
     * 肺活量計算結果情報
     *
     * @version 1.0.0
     */
    public static class BreathingCapacityCalcResult {

        /** 予測肺活量 */
        @JsonProperty("predict_breathing_capacity")
        private BigDecimal predictBreathingCapacity;
        /** 肺活量% */
        @JsonProperty("breathing_capacity_percentage")
        private BigDecimal breathingCapacityPercentage;

        /**
         * predictBreathingCapacityを返す
         *
         * @return predictBreathingCapacity
         */
        public BigDecimal getPredictBreathingCapacity() {
            return predictBreathingCapacity;
        }

        /**
         * predictBreathingCapacityを設定する
         *
         * @param predictBreathingCapacity
         *     予測肺活量
         */
        public void setPredictBreathingCapacity(BigDecimal predictBreathingCapacity) {
            this.predictBreathingCapacity = predictBreathingCapacity;
        }

        /**
         * breathingCapacityPercentageを返す
         *
         * @return breathingCapacityPercentage
         */
        public BigDecimal getBreathingCapacityPercentage() {
            return breathingCapacityPercentage;
        }

        /**
         * breathingCapacityPercentageを設定する
         *
         * @param breathingCapacityPercentage
         *     肺活量%
         */
        public void setBreathingCapacityPercentage(
                BigDecimal breathingCapacityPercentage) {
            this.breathingCapacityPercentage = breathingCapacityPercentage;
        }

    }

    /**
     * ユーザ健康情報
     *
     * @version 1.0.0
     */
    public static class UserData {

        /** 年齢 */
        @JsonProperty("age")
        private Integer age;
        /** 性別 */
        @JsonProperty("gender")
        private GenderType genderType;
        /** 身長 */
        @JsonProperty("height")
        private BigDecimal height;

        /**
         * ageを返す
         *
         * @return age
         */
        public Integer getAge() {
            return age;
        }

        /**
         * ageを設定する
         *
         * @param age
         *     年齢
         */
        public void setAge(Integer age) {
            this.age = age;
        }

        /**
         * genderTypeを返す
         *
         * @return genderType
         */
        public GenderType getGenderType() {
            return genderType;
        }

        /**
         * genderTypeを設定する
         *
         * @param genderType
         *     性別
         */
        public void setGenderType(GenderType genderType) {
            this.genderType = genderType;
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
    }

}
