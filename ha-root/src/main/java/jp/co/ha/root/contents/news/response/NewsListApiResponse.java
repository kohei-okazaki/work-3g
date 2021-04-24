package jp.co.ha.root.contents.news.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.root.base.JsonEntity;

/**
 * お知らせ情報一覧取得APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class NewsListApiResponse extends BaseRootApiResponse implements BaseApiResponse {

    /** お知らせ情報リスト */
    @JsonProperty("news_list")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<NewsDataResponse> newsDataResponseList;

    /**
     * newsDataResponseListを返す
     *
     * @return newsDataResponseList
     */
    public List<NewsDataResponse> getNewsDataResponseList() {
        return newsDataResponseList;
    }

    /**
     * newsDataResponseListを設定する
     *
     * @param newsDataResponseList
     *     お知らせ情報リスト
     */
    public void setNewsDataResponseList(List<NewsDataResponse> newsDataResponseList) {
        this.newsDataResponseList = newsDataResponseList;
    }

    /**
     * お知らせ情報クラス
     *
     * @version 1.0.0
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NewsDataResponse extends JsonEntity {

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
        /** タグ */
        @JsonProperty("tag")
        private Tag tag;

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
         *     ID
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
