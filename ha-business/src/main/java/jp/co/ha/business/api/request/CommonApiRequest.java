package jp.co.ha.business.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jp.co.ha.business.api.request.deserialize.RequestTypeDeserializer;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * API共通リクエスト
 *
 * @since 1.0
 */
public abstract class CommonApiRequest extends BaseApiRequest {

    /** リクエスト種別 */
    @JsonDeserialize(using = RequestTypeDeserializer.class)
    @Required(message = "requestTypeが未設定です")
    @JsonProperty("requestType")
    private RequestType requestType;
    /** ユーザID */
    @Required(message = "userIdが未設定です")
    @Pattern(regixPattern = RegexType.HALF_CHAR, message = "userIdが半角英数でありません")
    @Min(size = 2, message = "userIdが2byte未満です")
    @Max(size = 16, message = "userIdが16byte以上です")
    @JsonProperty("userId")
    private String userId;
    /** APIキー */
    @Mask
    @Required(message = "apiKeyが未設定です")
    @Length(length = 64, message = "apiKeyが64byteではありません")
    @JsonProperty("apiKey")
    private String apiKey;

    /**
     * デフォルトコンストラクタ
     */
    public CommonApiRequest() {
        super();
    }

    /**
     * requestTypeを返す
     *
     * @return requestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * requestTypeを設定する
     *
     * @param requestType
     *     リクエスト種別
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * userIdを返す
     *
     * @return userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * userIdを設定する
     *
     * @param userId
     *     ユーザID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * apiKeyを返す
     *
     * @return apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * apiKeyを設定する
     *
     * @param apiKey
     *     APIキー
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
