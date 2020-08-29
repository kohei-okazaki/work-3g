package jp.co.ha.db.entity.composite;

import java.io.Serializable;
import java.util.Date;

import jp.co.ha.common.db.annotation.Crypt;
import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * アカウント情報とメール情報と健康情報ファイル設定の複合Entity
 *
 * @version 1.0.0
 */
@Entity
public class CompositeAccount extends CompositeAccountKey implements Serializable {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 1L;
    /** パスワード */
    @Mask
    private String password;
    /** 削除フラグ */
    private String deleteFlag;
    /** 備考 */
    private String remarks;
    /** メールアドレス */
    @Mask
    @Crypt
    private String mailAddress;
    /** APIキー */
    @Mask
    private String apiKey;
    /** パスワード有効期限 */
    private Date passwordExpire;
    /** ヘッダ利用有無フラグ */
    private String headerFlag;
    /** フッタ利用有無フラグ */
    private String footerFlag;
    /** マスク利用有無フラグ */
    private String maskFlag;
    /** 囲み文字利用有無フラグ */
    private String enclosureCharFlag;

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
     * deleteFlagを返す
     *
     * @return deleteFlag
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * deleteFlagを設定する
     *
     * @param deleteFlag
     *     削除フラグ
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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
    public Date getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * passwordExpireを設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(Date passwordExpire) {
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
     *     ヘッダー利用有無
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
     *     フッター利用有無
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
     *     マスク利用有無
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
     *     囲み文字利用有無
     */
    public void setEnclosureCharFlag(String enclosureCharFlag) {
        this.enclosureCharFlag = enclosureCharFlag;
    }

}
