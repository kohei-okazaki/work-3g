package jp.co.ha.business.api.healthinfoapp.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;

/**
 * 健康情報登録リクエストAPIクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthInfoRegistApiRequest extends BaseAppApiRequest
        implements BaseApiRequest {

    /** 身長 */
    @Mask
    @Required(message = "heightが未設定です")
    @Pattern(regixPattern = RegexType.DECIMAL, message = "heightが数値でありません")
    @JsonProperty("height")
    private BigDecimal height;
    /** 体重 */
    @Mask
    @Required(message = "weightが未設定です")
    @Pattern(regixPattern = RegexType.DECIMAL, message = "weightが数値でありません")
    @JsonProperty("weight")
    private BigDecimal weight;
    /** トランザクションID */
    @JsonProperty("transaction_id")
    private String transactionId;

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
     * トランザクションIDを返す
     *
     * @return transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * トランザクションIDを設定する
     *
     * @param transactionId
     *     トランザクションID
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
