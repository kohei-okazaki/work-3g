package jp.co.ha.root.contents.news.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.dto.NewsListDto.Tag;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * おしらせ情報編集APIリクエストクラス
 *
 * @version 1.0.0
 */
public class NewsEditApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** 順序 */
    @JsonProperty("index")
    @Required
    private Integer index;
    /** タイトル */
    @JsonProperty("title")
    @Required
    private String title;
    /** 日付 */
    @JsonProperty("date")
    @Required
    private String date;
    /** 詳細 */
    @JsonProperty("detail")
    @Required
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
