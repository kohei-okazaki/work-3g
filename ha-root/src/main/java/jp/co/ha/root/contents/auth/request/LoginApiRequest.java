package jp.co.ha.root.contents.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * ログインAPIリクエストクラス
 *
 * @version 1.0.0
 */
public class LoginApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** ログインID */
    @JsonProperty("seq_login_id")
    private Integer seqLoginId;
    /** パスワード */
    @JsonProperty("password")
    private String password;

    /**
     * seqLoginIdを返す
     *
     * @return seqLoginId
     */
    public Integer getSeqLoginId() {
        return seqLoginId;
    }

    /**
     * seqLoginIdを設定する
     *
     * @param seqLoginId
     *     ログインID
     */
    public void setSeqLoginId(Integer seqLoginId) {
        this.seqLoginId = seqLoginId;
    }

    /**
     * passwordを返す
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * passwordを設定する
     *
     * @param password
     *     パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
