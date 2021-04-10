package jp.co.ha.root.contents.note.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * メモ一覧取得APIレスポンス情報
 *
 * @version 1.0.0
 */
public class NoteListApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** メモ情報リスト */
    @JsonProperty("note_list")
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
    public static class Note {

        /** メモ内容 */
        @JsonProperty("detail")
        private String detail;

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
}
