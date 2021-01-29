package jp.co.ha.root.contents.news.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsEditApiRequest;
import jp.co.ha.root.contents.news.response.NewsEditApiResponse;
import jp.co.ha.root.type.RootApiResult;

/**
 * お知らせ情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsEditApiController
        extends BaseRootApiController<NewsEditApiRequest, NewsEditApiResponse> {

    /**
     * 編集
     *
     * @param id
     *     お知らせ情報ID
     * @param request
     *     おしらせ情報編集APIリクエスト
     * @return おしらせ情報編集APIレスポンス
     */
    @PutMapping(value = "news/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NewsEditApiResponse edit(
            @PathVariable(name = "id", required = false) Optional<String> id,
            @RequestBody NewsEditApiRequest request) {
        // TODO 要実装

        NewsEditApiResponse response = new NewsEditApiResponse();
        response.setRootApiResult(RootApiResult.SUCCESS);

        return response;
    }

}
