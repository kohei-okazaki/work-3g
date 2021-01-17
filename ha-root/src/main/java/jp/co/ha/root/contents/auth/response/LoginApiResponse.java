package jp.co.ha.root.contents.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * ログインAPIレスポンスクラス
 *
 * @version 1.0.0
 */
public class LoginApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** 認証トークン */
    @JsonProperty("token")
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
     *     認証トークン
     */
    public void setToken(String token) {
        this.token = token;
    }

}
