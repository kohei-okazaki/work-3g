package jp.co.ha.root.contents.note.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * メモ登録APIリクエスト情報
 *
 * @version 1.0.0
 */
public class NoteEntryApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** 管理者サイトログイン情報ID */
    @JsonProperty("seq_login_id")
    private Long seqRootLoginInfoId;
    /** 件名 */
    @JsonProperty("title")
    private String title;
    /** メモ内容 */
    @JsonProperty("detail")
    private String detail;

    /**
     * seqRootLoginInfoIdを返す
     *
     * @return seqRootLoginInfoId
     */
    public Long getSeqRootLoginInfoId() {
        return seqRootLoginInfoId;
    }

    /**
     * seqRootLoginInfoIdを設定する
     *
     * @param seqRootLoginInfoId
     *     管理者サイトログイン情報ID
     */
    public void setSeqRootLoginInfoId(Long seqRootLoginInfoId) {
        this.seqRootLoginInfoId = seqRootLoginInfoId;
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
     *     件名
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * detailを返す
     *
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * detailを設定する
     *
     * @param detail
     *     メモ内容
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

}
