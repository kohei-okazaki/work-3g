package jp.co.ha.root.contents.news.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsEntiryApiRequest;
import jp.co.ha.root.contents.news.response.NewsEntiryApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * お知らせ情報登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsEntiryApiController
        extends BaseRootApiController<NewsEntiryApiRequest, NewsEntiryApiResponse> {

    /**
     * 登録
     *
     * @param request
     *     お知らせ情報登録APIリクエスト
     * @return お知らせ情報登録APIレスポンス
     */
    @PostMapping(value = "news/entry", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsEntiryApiResponse entry(@RequestBody NewsEntiryApiRequest request) {
        // TODO 要実装

        NewsEntiryApiResponse response = new NewsEntiryApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }
}
