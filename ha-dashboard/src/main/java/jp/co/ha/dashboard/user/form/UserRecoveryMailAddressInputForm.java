package jp.co.ha.dashboard.user.form;

import jakarta.validation.constraints.Email;

import jp.co.ha.common.validator.annotation.Max;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * ユーザ回復メールアドレス入力のForm
 *
 * @version 1.0.0
 */
public class UserRecoveryMailAddressInputForm implements BaseForm {

    /** メールアドレス */
    @Required(message = "メールアドレスが未入力です")
    @Max(size = 64, message = "メールアドレスは64桁以下で入力してください")
    @Email(message = "メールアドレス形式ではありません")
    private String mailAddress;

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

}
