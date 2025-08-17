package jp.co.ha.business.api.node.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.web.form.BaseApiResponse;

/**
 * Token発行APIのレスポンスクラス
 *
 * @version 1.0.0
 */
public class TokenApiResponse extends BaseNodeApiResponse implements BaseApiResponse {

    /** トークン */
    @Mask
    @JsonProperty("token")
    private String token;

    /**
     * トークンを返す
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * トークンを設定する
     *
     * @param token
     *     トークン
     */
    public void setToken(String token) {
        this.token = token;
    }

}
