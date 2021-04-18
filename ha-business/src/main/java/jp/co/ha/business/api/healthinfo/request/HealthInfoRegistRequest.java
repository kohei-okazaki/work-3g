package jp.co.ha.business.api.healthinfo.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.co.ha.business.api.healthinfo.type.TestMode;
import jp.co.ha.business.api.healthinfo.type.TestMode.TestModeDeserializer;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.common.web.form.BaseRestApiRequest;

/**
 * 健康情報登録リクエストクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthInfoRegistRequest extends BaseRestApiRequest
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
    /** テストモード種別 */
    @JsonDeserialize(using = TestModeDeserializer.class)
    @Required(message = "testModeが未設定です")
    @JsonProperty("testMode")
    private TestMode testMode;
    /** トランザクションID */
    @JsonProperty("transactionId")
    private Long transactionId;

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
     * testModeを返す
     *
     * @return testMode
     */
    public TestMode getTestMode() {
        return testMode;
    }

    /**
     * testModeを設定する
     *
     * @param testMode
     *     テストモード種別
     */
    public void setTestMode(TestMode testMode) {
        this.testMode = testMode;
    }

    /**
     * transactionIdを返す
     *
     * @return transactionId
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * transactionIdを設定する
     *
     * @param transactionId
     *     トランザクションID
     */
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

}
