package jp.co.ha.dashboard.user.form;

import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.MailAddress;
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
    @Length(length = 64, mode = LengthMode.LESS_EQUAL, message = "メールアドレスは64桁以下で入力してください")
    @MailAddress(message = "メールアドレス形式ではありません")
    private String mailAddress;

    /**
     * メールアドレスを返す
     *
     * @return mailAddress
     */
    public String getMailAddress() {
        return mailAddress;
    }

    /**
     * メールアドレスを設定する
     *
     * @param mailAddress
     *     メールアドレス
     */
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

}
