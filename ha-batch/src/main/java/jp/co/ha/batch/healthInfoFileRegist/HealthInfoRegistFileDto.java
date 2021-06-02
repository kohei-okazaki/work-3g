package jp.co.ha.batch.healthInfoFileRegist;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 健康情報一括登録情報JSONファイル
 *
 * @version 1.0.0
 */
public class HealthInfoRegistFileDto {

    /** ユーザID */
    @JsonProperty("seq_user_id")
    private Long seqUserId;
    /** 健康情報要求情報リスト */
    @JsonProperty("health_info_request_data_list")
    private List<HealthInfoRequestData> healthInfoRequestDataList = new ArrayList<>();

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
     *     ユーザID
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * healthInfoRequestDataListを返す
     *
     * @return healthInfoRequestDataList
     */
    public List<HealthInfoRequestData> getHealthInfoRequestDataList() {
        return healthInfoRequestDataList;
    }

    /**
     * healthInfoRequestDataListを設定する
     *
     * @param healthInfoRequestDataList
     *     健康情報要求情報リスト
     */
    public void setHealthInfoRequestDataList(
            List<HealthInfoRequestData> healthInfoRequestDataList) {
        this.healthInfoRequestDataList = healthInfoRequestDataList;
    }

    /**
     * 健康情報必須情報
     *
     * @version 1.0.0
     */
    public static class HealthInfoRequestData {

        /** 身長 */
        @JsonProperty("height")
        private BigDecimal height;
        /** 体重 */
        @JsonProperty("weight")
        private BigDecimal weight;

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

    }
}
