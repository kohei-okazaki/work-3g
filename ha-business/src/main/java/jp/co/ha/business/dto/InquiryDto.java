package jp.co.ha.business.dto;

/**
 * 問い合わせDTO
 * 
 * @version 1.0.0
 */
public class InquiryDto {

    /** ユーザID */
    private long seqUserId;
    /** 問い合わせ件名 */
    private String title;
    /** 問い合わせタイプ */
    private String type;
    /** 問い合わせ詳細内容 */
    private String body;

    /**
     * ユーザIDを返す
     * 
     * @return seqUserId
     */
    public long getSeqUserId() {
        return seqUserId;
    }

    /**
     * ユーザIDを設定する
     * 
     * @param seqUserId
     *     ユーザID
     */
    public void setSeqUserId(long seqUserId) {
        this.seqUserId = seqUserId;
    }

    /**
     * 問い合わせ件名を返す
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 問い合わせ件名を設定する
     * 
     * @param title
     *     問い合わせ件名
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 問い合わせタイプを返す
     * 
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * 問い合わせタイプを設定する
     * 
     * @param type
     *     問い合わせタイプ
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 問い合わせ詳細内容を返す
     * 
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * 問い合わせ詳細内容を設定する
     * 
     * @param body
     *     問い合わせ詳細内容
     */
    public void setBody(String body) {
        this.body = body;
    }

}
