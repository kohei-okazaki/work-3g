package jp.co.ha.dashboard.user.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.common.validator.annotation.Flag;
import jp.co.ha.common.validator.annotation.Future;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.MailAddress;
import jp.co.ha.common.validator.annotation.Past;
import jp.co.ha.common.validator.annotation.Pattern;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseForm;

/**
 * ユーザ設定画面フォームクラス
 *
 * @version 1.0.0
 */
public class UserSettingForm implements BaseForm {

    /** ユーザID */
    @Required(message = "ユーザIDが未入力です")
    private Long seqUserId;
    /** パスワード */
    @Mask
    @Required(message = "パスワードが未入力です")
    @Pattern(regixPattern = RegexType.HALF_CHAR, message = "パスワードが半角英数でありません")
    @Length(length = 2, mode = LengthMode.GREATER_EQUAL, message = "パスワードは2桁以上で入力してください")
    @Length(length = 16, mode = LengthMode.LESS_EQUAL, message = "パスワードは16桁以下で入力してください")
    private String password;
    /** 削除フラグ */
    @Required(message = "削除フラグが未入力です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "削除フラグが半角数字でありません")
    @Flag(message = "削除フラグの値が不正です")
    private String deleteFlag;
    /** 備考 */
    @Length(length = 256, mode = LengthMode.LESS_EQUAL, message = "備考は256桁以下で入力してください")
    private String remarks;
    /** メールアドレス */
    @Mask
    @Required(message = "メールアドレスが未入力です")
    @MailAddress(message = "メールアドレス形式ではありません")
    private String mailAddress;
    /** 性別 */
    @Required(message = "性別が未入力です")
    @Pattern(regixPattern = RegexType.HALF_NUMBER, message = "削除フラグが半角数字でありません")
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
    /** APIキー */
    @Mask
    @Required(message = "APIキーが未入力です")
    private String apiKey;
    /** パスワード有効期限 */
    @Required(message = "パスワード有効期限が未入力です")
    @Future(message = "パスワード有効期限は未来日を指定して下さい")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate passwordExpire;
    /** ヘッダ利用有無フラグ */
    @Required(message = "ヘッダ利用有無フラグが未入力です")
    @Flag(message = "ヘッダ利用有無フラグの値が不正です")
    private String headerFlag;
    /** フッタ利用有無フラグ */
    @Required(message = "フッタ利用有無フラグが未入力です")
    @Flag(message = "フッタ利用有無フラグの値が不正です")
    private String footerFlag;
    /** マスク利用有無フラグ */
    @Required(message = "マスク利用有無フラグが未入力です")
    @Flag(message = "マスク利用有無フラグの値が不正です")
    private String maskFlag;
    /** 囲み文字利用有無フラグ */
    @Required(message = "囲み文字利用有無フラグが未入力です")
    @Flag(message = "囲み文字利用有無フラグの値が不正です")
    private String enclosureCharFlag;

    /**
     * ユーザIDを返す
     *
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * ユーザIDを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
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
     * 削除フラグを返す
     *
     * @return deleteFlag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 削除フラグを設定する
     *
     * @param deleteFlag
     *     削除フラグ
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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
     * APIキーを返す
     *
     * @return apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * APIキーを設定する
     *
     * @param apiKey
     *     APIキー
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * パスワード有効期限日を返す
     *
     * @return passwordExpire
     */
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * パスワード有効期限日を設定する
     *
     * @param passwordExpire
     *     パスワード有効期限日
     */
    public void setPasswordExpire(LocalDate passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    /**
     * ヘッダー利用フラグを返す
     *
     * @return headerFlag
     */
    public String getHeaderFlag() {
        return headerFlag;
    }

    /**
     * ヘッダー利用フラグを設定する
     *
     * @param headerFlag
     *     ヘッダー利用フラグ
     */
    public void setHeaderFlag(String headerFlag) {
        this.headerFlag = headerFlag;
    }

    /**
     * フッター利用フラグを返す
     *
     * @return footerFlag
     */
    public String getFooterFlag() {
        return footerFlag;
    }

    /**
     * フッター利用フラグを設定する
     *
     * @param footerFlag
     *     フッター利用フラグ
     */
    public void setFooterFlag(String footerFlag) {
        this.footerFlag = footerFlag;
    }

    /**
     * マスク利用フラグを返す
     *
     * @return maskFlag
     */
    public String getMaskFlag() {
        return maskFlag;
    }

    /**
     * マスク利用フラグを設定する
     *
     * @param maskFlag
     *     マスク利用フラグ
     */
    public void setMaskFlag(String maskFlag) {
        this.maskFlag = maskFlag;
    }

    /**
     * 囲い文字利用フラグを返す
     *
     * @return enclosureCharFlag
     */
    public String getEnclosureCharFlag() {
        return enclosureCharFlag;
    }

    /**
     * 囲い文字利用フラグを設定する
     *
     * @param enclosureCharFlag
     *     囲い文字利用フラグ
     */
    public void setEnclosureCharFlag(String enclosureCharFlag) {
        this.enclosureCharFlag = enclosureCharFlag;
    }

}
