package jp.co.ha.web.form;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.co.ha.common.validator.annotation.Required;

/**
 * ユーザ認証が必要な基底リクエストクラス
 * 
 * @version 1.0.0
 */
public abstract class BaseUserAuthApiRequest extends BaseApiRequest {

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
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
