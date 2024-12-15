package jp.co.ha.business.dto;

/**
 * アカウントDTO
 *
 * @version 1.0.0
 */
public class AccountDto {

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
    private String passwordExpire;
    /** ヘッダ利用有無フラグ */
    private String headerFlag;
    /** フッタ利用有無フラグ */
    private String footerFlag;
    /** マスク利用有無フラグ */
    private String maskFlag;
    /** 囲み文字利用有無フラグ */
    private String enclosureCharFlag;

    /**
     * seqUserIdを返す
     *
     * @return seqUserId
     */
    public Long getSeqUserId() {
        return seqUserId;
    }

    /**
     * seqUserIdを設定する
     *
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
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

    /**
     * deleteFlagを返す
     * 
     * @return deleteFlag
     */
    public Boolean isDeleteFlag() {
        return deleteFlag;
    }

    /**
     * deleteFlagを設定する
     * 
     * @param deleteFlag
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

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
     * mailPasswordを返す
     *
     * @return mailPassword
     */
    public String getMailPassword() {
        return mailPassword;
    }

    /**
     * mailPasswordを設定する
     *
     * @param mailPassword
     *     メールパスワード
     *
     */
    public void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    /**
     * apiKeyを返す
     *
     * @return apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * apiKeyを設定する
     *
     * @param apiKey
     *     APIキー
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * passwordExpireを返す
     *
     * @return passwordExpire
     */
    public String getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * passwordExpireを設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(String passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    /**
     * headerFlagを返す
     *
     * @return headerFlag
     */
    public String getHeaderFlag() {
        return headerFlag;
    }

    /**
     * headerFlagを設定する
     *
     * @param headerFlag
     *     ヘッダ利用有無フラグ
     */
    public void setHeaderFlag(String headerFlag) {
        this.headerFlag = headerFlag;
    }

    /**
     * footerFlagを返す
     *
     * @return footerFlag
     */
    public String getFooterFlag() {
        return footerFlag;
    }

    /**
     * footerFlagを設定する
     *
     * @param footerFlag
     *     フッタ利用有無フラグ
     */
    public void setFooterFlag(String footerFlag) {
        this.footerFlag = footerFlag;
    }

    /**
     * maskFlagを返す
     *
     * @return maskFlag
     */
    public String getMaskFlag() {
        return maskFlag;
    }

    /**
     * maskFlagを設定する
     *
     * @param maskFlag
     *     マスク利用有無フラグ
     */
    public void setMaskFlag(String maskFlag) {
        this.maskFlag = maskFlag;
    }

    /**
     * enclosureCharFlagを返す
     *
     * @return enclosureCharFlag
     */
    public String getEnclosureCharFlag() {
        return enclosureCharFlag;
    }

    /**
     * enclosureCharFlagを設定する
     *
     * @param enclosureCharFlag
     *     囲み文字利用有無フラグ
     */
    public void setEnclosureCharFlag(String enclosureCharFlag) {
        this.enclosureCharFlag = enclosureCharFlag;
    }

}
