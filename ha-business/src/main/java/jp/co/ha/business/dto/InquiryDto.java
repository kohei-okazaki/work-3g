package jp.co.ha.business.dto;

/**
 * お問い合わせDto
 * 
 * @version 1.0.0
 */
public class InquiryDto {

    /** ユーザID */
    private Long seqUserId;
    /** 問い合わせ件名 */
    private String title;
    /** 問い合わせ種別 */
    private String type;
    /** 問い合わせ詳細内容 */
    private String body;

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
     */
    public void setSeqUserId(Long seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * titleを返す
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * titleを設定する
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * typeを返す
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * typeを設定する
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * bodyを返す
     * 
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * bodyを設定する
     * 
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

}
