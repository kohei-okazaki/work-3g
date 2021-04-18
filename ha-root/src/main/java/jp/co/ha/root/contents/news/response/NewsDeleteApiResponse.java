package jp.co.ha.root.contents.news.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.common.web.form.BaseApiResponse;
import jp.co.ha.root.base.BaseRootApiResponse;

/**
 * おしらせ情報削除APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class NewsDeleteApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** 削除したお知らせ情報ID */
    @JsonProperty("delete_news_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer deleteNewsId;

    /**
     * deleteNewsIdを返す
     *
     * @return deleteNewsId
     */
    public Integer getDeleteNewsId() {
        return deleteNewsId;
    }

    /**
     * deleteNewsIdを設定する
     *
     * @param deleteNewsId
     *     削除したお知らせ情報ID
     */
    public void setDeleteNewsId(Integer deleteNewsId) {
        this.deleteNewsId = deleteNewsId;
    }

}
