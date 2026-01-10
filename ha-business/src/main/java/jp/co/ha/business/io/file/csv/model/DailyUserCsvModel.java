package jp.co.ha.business.io.file.csv.model;

import java.math.BigDecimal;

import jp.co.ha.common.io.file.csv.model.BaseCsvModel;

/**
 * 日次ユーザ情報データ分析連携CSV Model
 *
 * @version 1.0.0
 */
public class DailyUserCsvModel implements BaseCsvModel {

    /** ユーザID */
    private Long seqUserId;
    /** 性別 */
    private int genderType;
    /** 誕生日 */
    private String birthDate;
    /** 削除フラグ */
    private Boolean deleteFlag;
    /** パスワード有効期限 */
    private String passwordExpire;
    /** 備考 */
    private String remarks;
    /** 目標体重 */
    private BigDecimal goalWeight;
    /** 登録日時 */
    private String regDate;
    /** 更新日時 */
    private String updateDate;
    /** ヘッダ利用有無フラグ */
    private Boolean headerFlag;
    /** フッタ利用有無フラグ */
    private Boolean footerFlag;
    /** マスク利用有無フラグ */
    private Boolean maskFlag;
    /** 囲み文字利用有無フラグ */
    private Boolean enclosureCharFlag;

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
     * パスワード有効期限を返す
     * 
     * @return passwordExpire
     */
    public String getPasswordExpire() {
        return passwordExpire;
    }

    /**
     * パスワード有効期限を設定する
     * 
     * @param passwordExpire
     *     パスワード有効期限
     */
    public void setPasswordExpire(String passwordExpire) {
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
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * 誕生日を設定する
     * 
     * @param birthDate
     *     誕生日
     */
    public void setBirthDate(String birthDate) {
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
    public String getRegDate() {
        return regDate;
    }

    /**
     * 登録日時を設定する
     * 
     * @param regDate
     *     登録日時
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    /**
     * 更新日時を返す
     * 
     * @return updateDate
     */
    public String getUpdateDate() {
        return updateDate;
    }

    /**
     * 更新日時を設定する
     * 
     * @param updateDate
     *     更新日時
     */
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * ヘッダ利用有無フラグを返す
     * 
     * @return headerFlag
     */
    public Boolean getHeaderFlag() {
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
    public Boolean getFooterFlag() {
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
    public Boolean getMaskFlag() {
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
    public Boolean getEnclosureCharFlag() {
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
