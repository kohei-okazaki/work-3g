package jp.co.ha.root.contents.note.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * メモ情報編集APIリクエスト情報
 *
 * @version 1.0.0
 */
public class NoteEditApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** 管理者サイトユーザメモ情報ID */
    @JsonProperty("seq_root_user_note_info_id")
    private Long seqRootUserNoteInfoId;
    /** 件名 */
    @JsonProperty("title")
    private String title;
    /** メモ内容 */
    @JsonProperty("detail")
    private String detail;

    /**
     * seqRootUserNoteInfoIdを返す
     *
     * @return seqRootUserNoteInfoId
     */
    public Long getSeqRootUserNoteInfoId() {
        return seqRootUserNoteInfoId;
    }

    /**
     * seqRootUserNoteInfoIdを設定する
     *
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     */
    public void setSeqRootUserNoteInfoId(Long seqRootUserNoteInfoId) {
        this.seqRootUserNoteInfoId = seqRootUserNoteInfoId;
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
