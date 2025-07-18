package jp.co.ha.dashboard.account.form;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.MailAddress;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * アカウント登録画面フォームクラス
 *
 * @version 1.0.0
 */
public class AccountRegistForm implements BaseForm {

    /** メールアドレス */
    @Required(message = "メールアドレスが未入力です")
    @Length(length = 16, mode = LengthMode.LESS_EQUAL, message = "メールアドレスは64桁以下で入力してください")
    @MailAddress(message = "メールアドレス形式ではありません")
    private String mailAddress;
    /** パスワード */
    @Mask
    @Required(message = "パスワードが未入力です")
    @Pattern(regixPattern = RegexType.HALF_CHAR, message = "パスワードが半角英数で入力してください")
    @Length(length = 2, mode = LengthMode.GREATER_EQUAL, message = "パスワードは2桁以上で入力してください")
    @Length(length = 16, mode = LengthMode.LESS_EQUAL, message = "パスワードは16桁以下で入力してください")
    private String password;
    /** 確認用パスワード */
    @Mask
    @Required(message = "確認用パスワードが未入力です")
    @Pattern(regixPattern = RegexType.HALF_CHAR, message = "確認用パスワードが半角英数で入力してください")
    @Length(length = 2, mode = LengthMode.GREATER_EQUAL, message = "確認用パスワードは2桁以上で入力してください")
    @Length(length = 16, mode = LengthMode.LESS_EQUAL, message = "確認用パスワードは16桁以下で入力してください")
    private String confirmPassword;
    /** 備考 */
    @Length(length = 256, mode = LengthMode.LESS_EQUAL, message = "備考は256桁以下で入力してください")
    private String remarks;

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

    /**
     * confirmPasswordを返す
     *
     * @return confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * confirmPasswordを設定する
     *
     * @param confirmPassword
     *     確認用パスワード
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * remarksを返す
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * remarksを設定する
     *
     * @param remarks
     *     備考
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
