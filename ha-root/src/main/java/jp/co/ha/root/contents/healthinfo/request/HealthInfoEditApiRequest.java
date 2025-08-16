package jp.co.ha.root.contents.healthinfo.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * 健康情報編集APIリクエスト
 *
 * @version 1.0.0
 */
public class HealthInfoEditApiRequest extends BaseRootApiRequest
        implements BaseApiRequest {

    /** ユーザID */
    @JsonProperty("seq_user_id")
    @Required(message = "seq_user_id is required")
    private Long seqUserId;

    /** 身長 */
    @JsonProperty("height")
    @Decimal(min = "0", message = "height is positive")
    @Required(message = "height is required")
    private BigDecimal height;

    /** 体重 */
    @JsonProperty("weight")
    @Decimal(min = "0", message = "weight is positive")
    @Required(message = "weight is required")
    private BigDecimal weight;

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

}
