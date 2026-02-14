package jp.co.ha.root.contents.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * ログインAPIリクエストクラス
 *
 * @version 1.0.0
 */
public class LoginApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** ログインID */
    @JsonProperty("seq_login_id")
    private Long seqLoginId;
    /** パスワード */
    @JsonProperty("password")
    private String password;

    /**
     * ログインIDを返す
     *
     * @return seqLoginId
     */
    public Long getSeqLoginId() {
        return seqLoginId;
    }

    /**
     * ログインIDを設定する
     *
     * @param seqLoginId
     *     ログインID
     */
    public void setSeqLoginId(Long seqLoginId) {
        this.seqLoginId = seqLoginId;
    }

    /**
     * パスワードを返す
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * パスワードを設定する
     *
     * @param password
     *     パスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
