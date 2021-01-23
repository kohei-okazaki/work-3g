package jp.co.ha.root.contents.news.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jp.co.ha.root.base.BaseRootApiResponse;
import jp.co.ha.root.contents.news.response.NewsListApiResponse.NewsDataResponse;
import jp.co.ha.web.form.BaseApiResponse;

/**
 * おしらせ情報登録APIレスポンスクラス
 *
 * @version 1.0.0
 */
public class NewsEntiryApiResponse extends BaseRootApiResponse
        implements BaseApiResponse {

    /** 登録したお知らせ情報 */
    @JsonProperty("news_data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private NewsDataResponse newsDataResponse;

    /**
     * newsDataResponseを返す
     *
     * @return newsDataResponse
     */
    public NewsDataResponse getNewsDataResponse() {
        return newsDataResponse;
    }

    /**
     * newsDataResponseを設定する
     *
     * @param newsDataResponse
     *     登録したお知らせ情報
     */
    public void setNewsDataResponse(NewsDataResponse newsDataResponse) {
        this.newsDataResponse = newsDataResponse;
    }

}
