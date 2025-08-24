package jp.co.ha.dashboard.user.form;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;

/**
 * ユーザ回復のForm
 *
 * @version 1.0.0
 */
public class UserRecoveryForm extends UserRecoveryMailAddressInputForm {

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

    /**
     * 確認用パスワードを返す
     *
     * @return confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * 確認用パスワードを設定する
     *
     * @param confirmPassword
     *     確認用パスワード
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
