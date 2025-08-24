package jp.co.ha.business.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ユーザDTO
 *
 * @version 1.0.0
 */
public class UserDto {

    /** ユーザID */
    private Long seqUserId;
    /** パスワード */
    private String password;
    /** 確認用パスワード */
    private String confirmPassword;
    /** 備考 */
    private String remarks;
    /** 削除フラグ */
    private Boolean deleteFlag;
    /** メールアドレス */
    private String mailAddress;
    /** メールパスワード */
    private String mailPassword;
    /** APIキー */
    private String apiKey;
    /** パスワード有効期限 */
    private LocalDate passwordExpire;
    /** 性別 */
    private String genderType;
    /** 誕生日 */
    private LocalDate birthDate;
    /** ヘッダ利用有無フラグ */
    private Boolean headerFlag;
    /** フッタ利用有無フラグ */
    private Boolean footerFlag;
    /** マスク利用有無フラグ */
    private Boolean maskFlag;
    /** 囲み文字利用有無フラグ */
    private Boolean enclosureCharFlag;
    /** 目標体重 */
    private BigDecimal goalWeight;

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
     * 削除フラグを返す
     * 
     * @return deleteFlag
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 削除フラグを設定する
     * 
     * @param deleteFlag
     *     削除フラグ
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
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
     * メールパスワードを返す
     *
     * @return mailPassword
     */
    public String getMailPassword() {
        return mailPassword;
    }

    /**
     * メールパスワードを設定する
     *
     * @param mailPassword
     *     メールパスワード
     *
     */
    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
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
     * パスワード有効期限を返す
     *
     * @return passwordExpire
     */
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * パスワード有効期限を設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(LocalDate passwordExpire) {
        this.passwordExpire = passwordExpire;
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
     * ヘッダーフラグを返す
     * 
     * @return headerFlag
     */
    public Boolean getHeaderFlag() {
        return headerFlag;
    }

    /**
     * ヘッダーフラグを設定する
     * 
     * @param headerFlag
     *     ヘッダーフラグ
     */
    public void setHeaderFlag(Boolean headerFlag) {
        this.headerFlag = headerFlag;
    }

    /**
     * フッターフラグを返す
     * 
     * @return footerFlag
     */
    public Boolean getFooterFlag() {
        return footerFlag;
    }

    /**
     * フッターフラグを設定する
     * 
     * @param footerFlag
     *     フッターフラグ
     */
    public void setFooterFlag(Boolean footerFlag) {
        this.footerFlag = footerFlag;
    }

    /**
     * マスクフラグを返す
     * 
     * @return maskFlag
     */
    public Boolean getMaskFlag() {
        return maskFlag;
    }

    /**
     * マスクフラグを設定する
     * 
     * @param maskFlag
     *     マスクフラグ
     */
    public void setMaskFlag(Boolean maskFlag) {
        this.maskFlag = maskFlag;
    }

    /**
     * 囲み文字利用フラグを返す
     * 
     * @return enclosureCharFlag
     */
    public Boolean getEnclosureCharFlag() {
        return enclosureCharFlag;
    }

    /**
     * 囲み文字利用フラグを設定する
     * 
     * @param enclosureCharFlag
     *     囲み文字利用フラグ
     */
    public void setEnclosureCharFlag(Boolean enclosureCharFlag) {
        this.enclosureCharFlag = enclosureCharFlag;
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

}
