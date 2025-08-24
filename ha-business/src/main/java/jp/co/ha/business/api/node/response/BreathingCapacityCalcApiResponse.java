package jp.co.ha.business.api.node.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.common.web.form.BaseApiResponse;

/**
 * 肺活量計算APIリクエストクラス
 *
 * @version 1.0.0
 */
public class BreathingCapacityCalcApiResponse extends BaseNodeApiResponse
        implements BaseApiResponse {

    /** 肺活量計算結果情報 */
    @JsonProperty("breathing_capacity_calc_result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BreathingCapacityCalcResult breathingCapacityCalcResult;
    /** ユーザ情報 */
    @JsonProperty("user_data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserData userData;

    /**
     * 肺活量計算結果情報を返す
     *
     * @return breathingCapacityCalcResult
     */
    public BreathingCapacityCalcResult getBreathingCapacityCalcResult() {
        return breathingCapacityCalcResult;
    }

    /**
     * 肺活量計算結果情報を設定する
     *
     * @param breathingCapacityCalcResult
     *     肺活量計算結果情報
     */
    public void setBreathingCapacityCalcResult(
            BreathingCapacityCalcResult breathingCapacityCalcResult) {
        this.breathingCapacityCalcResult = breathingCapacityCalcResult;
    }

    /**
     * ユーザ情報を返す
     *
     * @return userData
     */
    public UserData getUserData() {
        return userData;
    }

    /**
     * ユーザ情報を設定する
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
         * 予測肺活量を返す
         *
         * @return predictBreathingCapacity
         */
        public BigDecimal getPredictBreathingCapacity() {
            return predictBreathingCapacity;
        }

        /**
         * 予測肺活量を設定する
         *
         * @param predictBreathingCapacity
         *     予測肺活量
         */
        public void setPredictBreathingCapacity(BigDecimal predictBreathingCapacity) {
            this.predictBreathingCapacity = predictBreathingCapacity;
        }

        /**
         * 肺活量%を返す
         *
         * @return breathingCapacityPercentage
         */
        public BigDecimal getBreathingCapacityPercentage() {
            return breathingCapacityPercentage;
        }

        /**
         * 肺活量%を設定する
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
         * 年齢を返す
         *
         * @return age
         */
        public Integer getAge() {
            return age;
        }

        /**
         * 年齢を設定する
         *
         * @param age
         *     年齢
         */
        public void setAge(Integer age) {
            this.age = age;
        }

        /**
         * 性別を返す
         *
         * @return genderType
         */
        public GenderType getGenderType() {
            return genderType;
        }

        /**
         * 性別を設定する
         *
         * @param genderType
         *     性別
         */
        public void setGenderType(GenderType genderType) {
            this.genderType = genderType;
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
    }

}
