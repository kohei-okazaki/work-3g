package jp.co.ha.root.contents.news.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * おしらせ情報登録APIリクエストクラス
 *
 * @version 1.0.0
 */
public class NewsEntiryApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** タイトル */
    @JsonProperty("title")
    private String title;
    /** 日付 */
    @JsonProperty("date")
    private LocalDate date;
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
    public LocalDate getDate() {
        return date;
    }

    /**
     * dateを設定する
     *
     * @param date
     *     日付
     */
    public void setDate(LocalDate date) {
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
