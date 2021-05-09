package jp.co.ha.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * お知らせ情報Dto
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDto {

    /** お知らせ情報ID */
    @JsonProperty("id")
    private Long id;
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
     * idを返す
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * idを設定する
     *
     * @param id
     *     お知らせ情報ID
     */
    public void setId(Long id) {
        this.id = id;
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
        private String color;
        /** 名前 */
        @JsonProperty("name")
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
