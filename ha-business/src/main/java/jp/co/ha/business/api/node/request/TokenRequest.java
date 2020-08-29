package jp.co.ha.business.api.node.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.web.form.BaseApiRequest;

/**
 * Token発行APIのリクエストクラス
 *
 * @version 1.0.0
 */
public class TokenRequest extends BaseNodeRequest implements BaseApiRequest {

    /** ユーザID */
    @JsonProperty("user_id")
    private String userId;

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

}
