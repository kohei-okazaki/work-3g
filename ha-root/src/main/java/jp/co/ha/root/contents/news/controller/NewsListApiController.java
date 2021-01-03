package jp.co.ha.root.contents.news.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsListApiRequest;
import jp.co.ha.root.contents.news.response.NewsListApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * お知らせ情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsListApiController
        extends BaseRootApiController<NewsListApiRequest, NewsListApiResponse> {

    /**
     * お知らせ情報一覧取得
     *
     * @param request
     *     お知らせ情報一覧取得APIリクエスト
     * @return お知らせ情報一覧取得APIレスポンス
     */
    @GetMapping(value = "newslist", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsListApiResponse list(NewsListApiRequest request) {
        // TODO 要実装

        NewsListApiResponse response = new NewsListApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }

}
