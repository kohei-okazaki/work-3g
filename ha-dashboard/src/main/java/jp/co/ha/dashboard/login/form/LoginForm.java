package jp.co.ha.dashboard.login.form;

import javax.validation.constraints.Email;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * ログインフォームクラス
 *
 * @version 1.0.0
 */
public class LoginForm implements BaseForm {

    /** メールアドレス */
    @Required(message = "メールアドレスが未入力です")
    @Max(size = 64, message = "パスワードは64桁以下で入力してください")
    @Email(message = "メールアドレス形式ではありません")
    private String mailAddress;
    /** パスワード */
    @Mask
    @Required(message = "パスワードが未入力です")
    @Pattern(regixPattern = RegexType.HALF_CHAR, message = "パスワードが半角英数でありません")
    @Min(size = 2, message = "パスワードは2桁以上で入力してください")
    @Max(size = 16, message = "パスワードは16桁以下で入力してください")
    private String password;

    /**
     * mailAddressを返す
     *
     * @return mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * mailAddressを設定する
     *
     * @param mailAddress
     *     メールアドレス
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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
