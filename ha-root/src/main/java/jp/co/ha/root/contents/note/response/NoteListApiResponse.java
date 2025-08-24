package jp.co.ha.root.contents.note.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.common.web.form.JsonEntity;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * メモ情報一覧取得APIレスポンス情報
 *
 * @version 1.0.0
 */
public class NoteListApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** メモ情報リスト */
    @JsonProperty("note_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Note> noteList;

    /**
     * メモ情報リストを返す
     *
     * @return noteList
     */
    public List<Note> getNoteList() {
        return noteList;
    }

    /**
     * メモ情報リストを設定する
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
         * 管理者サイトユーザメモ情報IDを返す
         *
         * @return seqRootUserNoteInfoId
         */
        public Long getSeqRootUserNoteInfoId() {
            return seqRootUserNoteInfoId;
        }

        /**
         * 管理者サイトユーザメモ情報IDを設定する
         *
         * @param seqRootUserNoteInfoId
         *     管理者サイトユーザメモ情報ID
         */
        public void setSeqRootUserNoteInfoId(Long seqRootUserNoteInfoId) {
            this.seqRootUserNoteInfoId = seqRootUserNoteInfoId;
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
}
