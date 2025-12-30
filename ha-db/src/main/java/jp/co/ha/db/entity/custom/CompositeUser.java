package jp.co.ha.db.entity.custom;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Crypt;
import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.common.log.annotation.Mask;
import jp.co.ha.db.entity.UserKey;

/**
 * ユーザ情報と健康情報ファイル設定の複合Entity
 *
 * @version 1.0.0
 */
@Entity
public class CompositeUser extends UserKey {

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
    /** 目標体重 */
    private BigDecimal goalWeight;
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
    public Boolean isDeleteFlag() {
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
    public int getGenderType() {
        return genderType;
    }

    /**
     * 性別を設定する
     * 
     * @param genderType
     *     性別
     */
    public void setGenderType(int genderType) {
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
     * 登録日時を返す
     *
     * @return regDate
     */
    public LocalDateTime getRegDate() {
        return regDate;
    }

    /**
     * 登録日時を設定する
     *
     * @param regDate
     *     登録日時
     */
    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    /**
     * 更新日時を返す
     *
     * @return updateDate
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * 更新日時を設定する
     *
     * @param updateDate
     *     更新日時
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * ヘッダ利用有無フラグを返す
     * 
     * @return headerFlag
     */
    public Boolean isHeaderFlag() {
        return headerFlag;
    }

    /**
     * ヘッダ利用有無フラグを設定する
     * 
     * @param headerFlag
     *     ヘッダ利用有無フラグ
     */
    public void setHeaderFlag(Boolean headerFlag) {
        this.headerFlag = headerFlag;
    }

    /**
     * フッタ利用有無フラグを返す
     * 
     * @return footerFlag
     */
    public Boolean isFooterFlag() {
        return footerFlag;
    }

    /**
     * フッタ利用有無フラグを設定する
     * 
     * @param footerFlag
     *     フッタ利用有無フラグ
     */
    public void setFooterFlag(Boolean footerFlag) {
        this.footerFlag = footerFlag;
    }

    /**
     * マスク利用有無フラグを返す
     * 
     * @return maskFlag
     */
    public Boolean isMaskFlag() {
        return maskFlag;
    }

    /**
     * マスク利用有無フラグを設定する
     * 
     * @param maskFlag
     *     マスク利用有無フラグ
     */
    public void setMaskFlag(Boolean maskFlag) {
        this.maskFlag = maskFlag;
    }

    /**
     * 囲み文字利用有無フラグを返す
     * 
     * @return enclosureCharFlag
     */
    public Boolean isEnclosureCharFlag() {
        return enclosureCharFlag;
    }

    /**
     * 囲み文字利用有無フラグを設定する
     * 
     * @param enclosureCharFlag
     *     囲み文字利用有無フラグ
     */
    public void setEnclosureCharFlag(Boolean enclosureCharFlag) {
        this.enclosureCharFlag = enclosureCharFlag;
    }

}
