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
    /** タグ */
    @JsonProperty("tag")
    private Tag tag;

    /**
     * seqNewsInfoIdを返す
     *
     * @return seqNewsInfoId
     */
    public Long getSeqNewsInfoId() {
        return seqNewsInfoId;
    }

    /**
     * seqNewsInfoIdを設定する
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     */
    public void setSeqNewsInfoId(Long seqNewsInfoId) {
        this.seqNewsInfoId = seqNewsInfoId;
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
     *     タイトル
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * dateを返す
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * dateを設定する
     *
     * @param date
     *     日付
     */
    public void setDate(String date) {
        this.date = date;
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
     *     詳細
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * tagを返す
     *
     * @return tag
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * tagを設定する
     *
     * @param tag
     *     タグ
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
         * colorを返す
         *
         * @return color
         */
        public String getColor() {
            return color;
        }

        /**
         * colorを設定する
         *
         * @param color
         *     色
         */
        public void setColor(String color) {
            this.color = color;
        }

        /**
         * nameを返す
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * nameを設定する
         *
         * @param name
         *     名前
         */
        public void setName(String name) {
            this.name = name;
        }
    }
}
