package jp.co.ha.web.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jp.co.ha.common.validator.annotation.Required;

/**
 * RestAPIの基底リクエストクラス
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseRestApiRequest extends BaseApiRequest {

    /** ユーザID */
    @JsonIgnore
    @Required(message = "userIdが未指定です")
    private String userId;
    /** APIキー */
    @JsonIgnore
    @Required(message = "apiKeyが未指定です")
    private String apiKey;

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
