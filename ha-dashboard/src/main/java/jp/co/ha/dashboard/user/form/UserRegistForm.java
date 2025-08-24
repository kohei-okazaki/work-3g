package jp.co.ha.dashboard.user.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.MailAddress;
import jp.co.ha.common.validator.annotation.Past;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * ユーザ登録画面フォームクラス
 *
 * @version 1.0.0
 */
public class UserRegistForm implements BaseForm {

    /** メールアドレス */
    @Required(message = "メールアドレスが未入力です")
    @Length(length = 64, mode = LengthMode.LESS_EQUAL, message = "メールアドレスは64桁以下で入力してください")
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
    /** 性別 */
    @Required(message = "性別が未入力です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "性別が半角数字でありません")
    private String genderType;
    /** 誕生日 */
    @Required(message = "誕生日が未入力です")
    @Past(message = "誕生日は過去日を指定して下さい")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    /** 目標体重 */
    @Required(message = "目標体重が未入力です")
    @Decimal(min = "1", max = "999", message = "目標体重は1kg～999kgまでの範囲が指定可能です")
    private BigDecimal goalWeight;
    /** 備考 */
    @Length(length = 256, mode = LengthMode.LESS_EQUAL, message = "備考は256桁以下で入力してください")
    private String remarks;

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

    /**
     * 性別を返す
     * 
     * @return genderType
     */
    public String getGenderType() {
        return genderType;
    }

    /**
     * 性別を設定する
     * 
     * @param genderType
     *     性別
     */
    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    /**
     * 誕生日を返す
     * 
     * @return birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * 誕生日を設定する
     * 
     * @param birthDate
     *     誕生日
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * 目標体重を返す
     * 
     * @return goalWeight
     */
    public BigDecimal getGoalWeight() {
        return goalWeight;
    }

    /**
     * 目標体重を設定する
     * 
     * @param goalWeight
     *     目標体重
     */
    public void setGoalWeight(BigDecimal goalWeight) {
        this.goalWeight = goalWeight;
    }

    /**
     * 備考を返す
     *
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 備考を設定する
     *
     * @param remarks
     *     備考
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
