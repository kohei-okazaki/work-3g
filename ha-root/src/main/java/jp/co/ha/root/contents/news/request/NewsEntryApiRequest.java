package jp.co.ha.root.contents.news.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.business.dto.NewsListDto.Tag;
import jp.co.ha.root.base.BaseRootApiRequest;
import jp.co.ha.web.form.BaseApiRequest;

/**
 * おしらせ情報登録APIリクエストクラス
 *
 * @version 1.0.0
 */
public class NewsEntryApiRequest extends BaseRootApiRequest implements BaseApiRequest {

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
