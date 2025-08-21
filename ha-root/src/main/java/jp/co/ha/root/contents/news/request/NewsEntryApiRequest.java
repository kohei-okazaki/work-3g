package jp.co.ha.root.contents.news.request;

import jakarta.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.dto.NewsDto.Tag;
import jp.co.ha.common.validator.annotation.Required;
import jp.co.ha.common.web.form.BaseApiRequest;
import jp.co.ha.root.base.BaseRootApiRequest;

/**
 * おしらせ情報登録APIリクエストクラス
 *
 * @version 1.0.0
 */
public class NewsEntryApiRequest extends BaseRootApiRequest implements BaseApiRequest {

    /** タイトル */
    @JsonProperty("title")
    @Required(message = "title is required")
    private String title;
    /** 日付 */
    @JsonProperty("date")
    @Required(message = "date is required")
    private String date;
    /** 詳細 */
    @JsonProperty("detail")
    @Required(message = "detail is required")
    private String detail;
    /** タグ */
    @Valid
    @JsonProperty("tag")
    @Required(message = "tag is required")
    private Tag tag;

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

}
