package jp.co.ha.root.contents.news.response;

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
 * お知らせ情報一覧取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class NewsListApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** お知らせ情報リスト */
    @JsonProperty("news_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<News> newsList;

    /**
     * お知らせ情報リスト を返す
     *
     * @return newsList
     */
    public List<News> getNewsList() {
        return newsList;
    }

    /**
     * お知らせ情報リスト を設定する
     *
     * @param newsList
     *     お知らせ情報リスト
     */
    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    /**
     * お知らせ情報クラス
     *
     * @version 1.0.0
     */
    public static class News extends JsonEntity {

        /** お知らせ情報ID */
        @JsonProperty("seq_news_info_id")
        private Long seqNewsInfoId;
        /** タイトル */
        @JsonProperty("title")
        private String title;
        /** 日付 */
        @JsonProperty("date")
        private String date;
        /** 詳細 */
        @JsonProperty("detail")
        private String detail;
        /** タグ */
        @JsonProperty("tag")
        private Tag tag;
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
         * お知らせ情報IDを返す
         *
         * @return seqNewsInfoId
         */
        public Long getSeqNewsInfoId() {
            return seqNewsInfoId;
        }

        /**
         * お知らせ情報IDを設定する
         *
         * @param seqNewsInfoId
         *     お知らせ情報ID
         */
        public void setSeqNewsInfoId(Long seqNewsInfoId) {
            this.seqNewsInfoId = seqNewsInfoId;
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
         * 日付を返す
         *
         * @return date
         */
        public String getDate() {
            return date;
        }

        /**
         * 日付を設定する
         *
         * @param date
         *     日付
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * 詳細を返す
         *
         * @return detail
         */
        public String getDetail() {
            return detail;
        }

        /**
         * 詳細を設定する
         *
         * @param detail
         *     詳細
         */
        public void setDetail(String detail) {
            this.detail = detail;
        }

        /**
         * タグを返す
         *
         * @return tag
         */
        public Tag getTag() {
            return tag;
        }

        /**
         * タグを設定する
         *
         * @param tag
         *     タグ
         */
        public void setTag(Tag tag) {
            this.tag = tag;
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

    /**
     * タグ情報
     *
     * @version 1.0.0
     */
    public static class Tag {

        /** 色 */
        @JsonProperty("color")
        private String color;
        /** 名前 */
        @JsonProperty("name")
        private String name;

        /**
         * 色を返す
         *
         * @return color
         */
        public String getColor() {
            return color;
        }

        /**
         * 色を設定する
         *
         * @param color
         *     色
         */
        public void setColor(String color) {
            this.color = color;
        }

        /**
         * 名前を返す
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * 名前を設定する
         *
         * @param name
         *     名前
         */
        public void setName(String name) {
            this.name = name;
        }
    }
}
