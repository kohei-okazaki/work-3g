package jp.co.ha.root.contents.user.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * ユーザ登録APIリクエストクラス
 *
 * @version 1.0.0
 */
public class UserEntryApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** パスワード */
    @JsonProperty("password")
    @Required(message = "password is required")
    private String password;
    /** 確認用パスワード */
    @JsonProperty("conf_password")
    @Required(message = "conf_password is required")
    private String confPassword;

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

    /**
     * confPasswordを返す
     *
     * @return confPassword
     */
    public String getConfPassword() {
        return confPassword;
    }

    /**
     * confPasswordを設定する
     *
     * @param confPassword
     *     確認用パスワード
     */
    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

}
