package jp.co.ha.business.api.node.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.LogMessageFactory;
import jp.co.ha.common.web.form.BaseApiRequest;

/**
 * 基礎健康情報計算APIのリクエストクラス
 *
 * @version 1.0.0
 */
public class BasicHealthInfoCalcApiRequest extends BaseNodeApiRequest
        implements BaseApiRequest {

    /** 身長 */
    @JsonProperty("height")
    private BigDecimal height;
    /** 体重 */
    @JsonProperty("weight")
    private BigDecimal weight;

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

    @Override
    public String toString() {
        return LogMessageFactory.toString(this);
    }

}
