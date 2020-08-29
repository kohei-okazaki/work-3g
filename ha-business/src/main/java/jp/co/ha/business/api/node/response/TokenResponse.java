package jp.co.ha.business.api.node.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * Token発行APIのレスポンスクラス
 *
 * @version 1.0.0
 */
public class TokenResponse extends BaseNodeResponse implements BaseApiResponse {

    /** トークン */
    @JsonProperty("token")
    @Mask
    private String token;

    /**
     * tokenを返す
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * tokenを設定する
     *
     * @param token
     *     トークン
     */
    public void setToken(String token) {
        this.token = token;
    }

}
