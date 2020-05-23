package jp.co.ha.business.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * お知らせ情報一覧リストDto
 *
 * @version 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsListDto {

    /** お知らせ情報一覧リスト */
    @JsonProperty("news_list")
    private List<NewsDto> newsDtoList;

    /**
     * newsDtoListを返す
     *
     * @return newsDtoList
     */
    public List<NewsDto> getNewsDtoList() {
        return newsDtoList;
    }

    /**
     * newsDtoListを設定する
     *
     * @param newsDtoList
     *     お知らせ情報一覧リスト
     */
    public void setNewsDtoList(List<NewsDto> newsDtoList) {
        this.newsDtoList = newsDtoList;
    }

    /**
     * お知らせ情報Dto
     *
     * @version 1.0.0
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NewsDto {

        /** 順序 */
        @JsonProperty("index")
        private Integer index;
        /** タイトル */
        @JsonProperty("title")
        private String title;
        /** 日付 */
        @JsonProperty("date")
        private String date;
        /** 詳細 */
        @JsonProperty("detail")
        private String detail;
        /** タグ色 */
        @JsonProperty("tag_color")
        private String tagColor;
        /** タグ名 */
        @JsonProperty("tag_name")
        private String tagName;

        /**
         * indexを返す
         *
         * @return index
         */
        public Integer getIndex() {
            return index;
        }

        /**
         * indexを設定する
         *
         * @param index
         *     順序
         */
        public void setIndex(Integer index) {
            this.index = index;
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
         * tagColorを返す
         *
         * @return tagColor
         */
        public String getTagColor() {
            return tagColor;
        }

        /**
         * tagColorを設定する
         *
         * @param tagColor
         *     タグ色
         */
        public void setTagColor(String tagColor) {
            this.tagColor = tagColor;
        }

        /**
         * tagNameを返す
         *
         * @return tagName
         */
        public String getTagName() {
            return tagName;
        }

        /**
         * tagNameを設定する
         *
         * @param tagName
         *     タグ名
         */
        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

    }
}
