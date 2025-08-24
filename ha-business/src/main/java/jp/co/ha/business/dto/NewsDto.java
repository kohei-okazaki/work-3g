package jp.co.ha.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.validator.annotation.Required;

/**
 * お知らせ情報Dto
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDto {

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
    /** タグ情報 */
    @JsonProperty("tag")
    private Tag tag;

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
     * タグ情報を返す
     *
     * @return tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * タグ情報を設定する
     *
     * @param tag
     *     タグ情報
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * タグ情報
     *
     * @version 1.0.0
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tag {

        /** 色 */
        @JsonProperty("color")
        @Required(message = "color is required")
        private String color;
        /** 名前 */
        @JsonProperty("name")
        @Required(message = "name is required")
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
