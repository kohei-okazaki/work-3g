package jp.co.ha.business.api.node.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.LogMessageFactory;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * 基礎健康情報計算APIのリクエストクラス
 *
 * @version 1.0.0
 */
public class BasicHealthInfoCalcRequest extends BaseNodeRequest
        implements BaseApiRequest {

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

    @Override
    public String toString() {
        return LogMessageFactory.toString(this);
    }

}
