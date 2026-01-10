package jp.co.ha.db.entity.custom;

import java.time.LocalDateTime;

import jp.co.ha.common.db.annotation.Entity;
import jp.co.ha.common.log.annotation.Ignore;
import jp.co.ha.db.entity.InquiryManagementKey;

/**
 * 以下のテーブルの複合Entity
 * <ul>
 * <li>問い合わせ管理情報</li>
 * <li>問い合わせステータスマスタ</li>
 * <li>問い合わせ種別マスタ</li>
 * </ul>
 *
 * @version 1.0.0
 */
@Entity
public class CompositeInquiry extends InquiryManagementKey {

    /** serialVersionUID */
    @Ignore
    private static final long serialVersionUID = 6656616182654307665L;
    /** ユーザID */
    private Long seqUserId;
    /** 対応者ログインID */
    private Long responderLoginId;
    /** 問い合わせステータス */
    private String inquiryStatus;
    /** 問い合わせステータス名 */
    private String inquiryStatusName;
    /** 問い合わせ種別 */
    private String inquiryType;
    /** 問い合わせ種別名 */
    private String inquiryTypeName;
    /** タイトル */
    private String title;
    /** S3キー */
    private String s3Key;
    /** 登録日時 */
    private LocalDateTime regDate;
    /** 更新日時 */
    private LocalDateTime updateDate;

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
     * 対応者ログインIDを返す
     * 
     * @return responderLoginId
     */
    public Long getResponderLoginId() {
        return responderLoginId;
    }

    /**
     * 対応者ログインIDを設定する
     * 
     * @param responderLoginId
     *     対応者ログインID
     */
    public void setResponderLoginId(Long responderLoginId) {
        this.responderLoginId = responderLoginId;
    }

    /**
     * 問い合わせステータスを返す
     * 
     * @return inquiryStatus
     */
    public String getInquiryStatus() {
        return inquiryStatus;
    }

    /**
     * 問い合わせステータスを設定する
     * 
     * @param inquiryStatus
     *     問い合わせステータス
     */
    public void setInquiryStatus(String inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }

    /**
     * 問い合わせステータス名を返す
     * 
     * @return inquiryStatusName
     */
    public String getInquiryStatusName() {
        return inquiryStatusName;
    }

    /**
     * 問い合わせステータス名を設定する
     * 
     * @param inquiryStatusName
     *     問い合わせステータス名
     */
    public void setInquiryStatusName(String inquiryStatusName) {
        this.inquiryStatusName = inquiryStatusName;
    }

    /**
     * 問い合わせ種別を返す
     * 
     * @return inquiryType
     */
    public String getInquiryType() {
        return inquiryType;
    }

    /**
     * 問い合わせ種別を設定する
     * 
     * @param inquiryType
     *     問い合わせ種別
     */
    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    /**
     * 問い合わせ種別名を返す
     * 
     * @return inquiryTypeName
     */
    public String getInquiryTypeName() {
        return inquiryTypeName;
    }

    /**
     * 問い合わせ種別名を設定する
     * 
     * @param inquiryTypeName
     *     問い合わせ種別名
     */
    public void setInquiryTypeName(String inquiryTypeName) {
        this.inquiryTypeName = inquiryTypeName;
    }

    /**
     * タイトルを返す
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * タイトルを設定する
     * 
     * @param title
     *     タイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * S3キーを返す
     * 
     * @return s3Key
     */
    public String getS3Key() {
        return s3Key;
    }

    /**
     * S3キーを設定する
     * 
     * @param s3Key
     *     S3キー
     */
    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
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

}
