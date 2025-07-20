package jp.co.ha.db.entity.composite;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Crypt;
import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;

/**
 * ユーザ情報と健康情報ファイル設定の複合Entity
 *
 * @version 1.0.0
 */
@Entity
public class CompositeUser extends CompositeUserKey {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 1L;
    /** パスワード */
    @Mask
    private String password;
    /** 削除フラグ */
    private Boolean deleteFlag;
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
    private LocalDate passwordExpire;
    /** 性別 */
    private int genderType;
    /** 誕生日 */
    private LocalDate birthDate;
    /** 登録日時 */
    private LocalDateTime regDate;
    /** 更新日時 */
    private LocalDateTime updateDate;
    /** ヘッダ利用有無フラグ */
    private Boolean headerFlag;
    /** フッタ利用有無フラグ */
    private Boolean footerFlag;
    /** マスク利用有無フラグ */
    private Boolean maskFlag;
    /** 囲み文字利用有無フラグ */
    private Boolean enclosureCharFlag;

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
    public LocalDate getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * passwordExpireを設定する
     *
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(LocalDate passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    /**
     * genderTypeを返す
     * 
     * @return genderType
     */
    public int getGenderType() {
        return genderType;
    }

    /**
     * genderTypeを設定する
     * 
     * @param genderType
     *     性別
     */
    public void setGenderType(int genderType) {
        this.genderType = genderType;
    }

    /**
     * birthDateを返す
     * 
     * @return birthDate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * birthDateを設定する
     * 
     * @param birthDate
     *     誕生日
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * regDateを返す
     *
     * @return regDate
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * regDateを設定する
     *
     * @param regDate
     *     登録日時
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * updateDateを返す
     *
     * @return updateDate
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * updateDateを設定する
     *
     * @param updateDate
     *     更新日時
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * headerFlagを返す
     * 
     * @return headerFlag
     */
    public Boolean isHeaderFlag() {
        return headerFlag;
    }

    /**
     * headerFlagを設定する
     * 
     * @param headerFlag
     */
    public void setHeaderFlag(Boolean headerFlag) {
        this.headerFlag = headerFlag;
    }

    /**
     * footerFlagを返す
     * 
     * @return footerFlag
     */
    public Boolean isFooterFlag() {
        return footerFlag;
    }

    /**
     * footerFlagを設定する
     * 
     * @param footerFlag
     */
    public void setFooterFlag(Boolean footerFlag) {
        this.footerFlag = footerFlag;
    }

    /**
     * maskFlagを返す
     * 
     * @return maskFlag
     */
    public Boolean isMaskFlag() {
        return maskFlag;
    }

    /**
     * maskFlagを設定する
     * 
     * @param maskFlag
     */
    public void setMaskFlag(Boolean maskFlag) {
        this.maskFlag = maskFlag;
    }

    /**
     * enclosureCharFlagを返す
     * 
     * @return enclosureCharFlag
     */
    public Boolean isEnclosureCharFlag() {
        return enclosureCharFlag;
    }

    /**
     * enclosureCharFlagを設定する
     * 
     * @param enclosureCharFlag
     */
    public void setEnclosureCharFlag(Boolean enclosureCharFlag) {
        this.enclosureCharFlag = enclosureCharFlag;
    }

}
