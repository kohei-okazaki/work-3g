package jp.co.ha.root.contents.note.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.root.base.JsonEntity;

/**
 * メモ一覧取得APIレスポンス情報
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteListApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** メモ情報リスト */
    @JsonProperty("note_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Note> noteList;

    /**
     * noteListを返す
     *
     * @return noteList
     */
    public List<Note> getNoteList() {
        return noteList;
    }

    /**
     * noteListを設定する
     *
     * @param noteList
     *     メモ情報リスト
     */
    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    /**
     * メモ情報
     *
     * @version 1.0.0
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Note extends JsonEntity {

        /** 管理者サイトユーザメモ情報ID */
        @JsonProperty("seq_root_user_note_info_id")
        private Long seqRootUserNoteInfoId;
        /** 件名 */
        @JsonProperty("title")
        private String title;
        /** メモ内容 */
        @JsonProperty("detail")
        private String detail;
        /** 登録日時 */
        @JsonProperty("reg_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime regDate;
        /** 更新日時 */
        @JsonProperty("update_date")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Tokyo")
        private LocalDateTime updateDate;

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

    }
}
