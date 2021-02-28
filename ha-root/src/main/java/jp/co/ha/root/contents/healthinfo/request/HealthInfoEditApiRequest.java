package jp.co.ha.root.contents.healthinfo.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * 健康情報編集APIリクエスト
 *
 * @version 1.0.0
 */
public class HealthInfoEditApiRequest extends BaseRootApiRequest
        implements BaseApiRequest {

    /** ユーザID */
    @JsonProperty("seq_user_id")
    private Integer seqUserId;
    /** 身長 */
    @JsonProperty("height")
    private BigDecimal height;
    /** 体重 */
    @JsonProperty("weight")
    private BigDecimal weight;

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

}
