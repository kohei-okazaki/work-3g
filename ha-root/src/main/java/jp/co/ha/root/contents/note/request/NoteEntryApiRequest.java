package jp.co.ha.root.contents.note.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.validator.LengthMode;
import jp.co.ha.common.validator.annotation.Length;
import jp.co.ha.common.validator.annotation.Min;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * メモ情報登録APIリクエスト情報
 *
 * @version 1.0.0
 */
public class NoteEntryApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** 管理者サイトログイン情報ID */
    @JsonProperty("seq_login_id")
    @Required(message = "seq_login_id is required")
    @Min(size = 0, message = "seq_login_id is positive")
    private Long seqRootLoginInfoId;
    /** 件名 */
    @JsonProperty("title")
    @Required(message = "title is required")
    @Length(length = 30, mode = LengthMode.LESS_EQUAL, message = "title is less equal 30 byte")
    private String title;
    /** メモ内容 */
    @JsonProperty("detail")
    @Required(message = "detail is required")
    private String detail;

    /**
     * 管理者サイトログイン情報IDを返す
     *
     * @return seqRootLoginInfoId
     */
    public Long getSeqRootLoginInfoId() {
        return seqRootLoginInfoId;
    }

    /**
     * 管理者サイトログイン情報IDを設定する
     *
     * @param seqRootLoginInfoId
     *     管理者サイトログイン情報ID
     */
    public void setSeqRootLoginInfoId(Long seqRootLoginInfoId) {
        this.seqRootLoginInfoId = seqRootLoginInfoId;
    }

    /**
     * 件名を返す
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 件名を設定する
     *
     * @param title
     *     件名
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * メモ内容を返す
     *
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * メモ内容を設定する
     *
     * @param detail
     *     メモ内容
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

}
