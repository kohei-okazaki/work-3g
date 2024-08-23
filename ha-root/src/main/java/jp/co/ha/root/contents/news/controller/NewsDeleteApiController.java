package jp.co.ha.root.contents.news.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.delete.NewsInfoDeleteService;
import jp.co.ha.business.db.crud.delete.impl.NewsInfoDeleteServiceImpl;
import jp.co.ha.business.db.crud.read.NewsInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.NewsInfoSearchServiceImpl;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.component.NewsComponent;
import jp.co.ha.root.contents.news.request.NewsDeleteApiRequest;
import jp.co.ha.root.contents.news.response.NewsDeleteApiResponse;

/**
 * お知らせ情報削除APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsDeleteApiController
        extends BaseRootApiController<NewsDeleteApiRequest, NewsDeleteApiResponse> {

    /** {@linkplain NewsInfoSearchServiceImpl} */
    @Autowired
    private NewsInfoSearchService searchService;
    /** {@linkplain NewsInfoDeleteServiceImpl} */
    @Autowired
    private NewsInfoDeleteService deleteService;
    /** {@linkplain NewsComponent} */
    @Autowired
    private NewsComponent newsComponent;

    /**
     * お知らせ情報削除
     *
     * @param id
     *     お知らせ情報ID
     * @param request
     *     おしらせ情報削除APIリクエスト
     * @return おしらせ情報削除APIレスポンス
     * @throws BaseException
     *     URLが不正な場合
     */
    @DeleteMapping(value = "news/{seq_news_info_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public NewsDeleteApiResponse delete(
            @PathVariable(name = "seq_news_info_id", required = false) Optional<Long> id,
            NewsDeleteApiRequest request) throws BaseException {

        if (!id.isPresent()) {
            // URLが不正場合
            return getErrorResponse("id is required");
        }

        // お知らせ情報ID
        Long seqNewsInfoId = Long.valueOf(id.get());

        // お知らせ情報を検索
        Optional<NewsInfo> optional = searchService.findById(seqNewsInfoId);
        if (!optional.isPresent()) {
            return getErrorResponse("news_info is not found");
        }

        // お知らせ情報を削除
        deleteService.delete(seqNewsInfoId);
        // お知らせ情報JSONを削除
        newsComponent.remove(optional.get().getS3Key());
        // Slack通知
        newsComponent.sendSlack(seqNewsInfoId);

        NewsDeleteApiResponse response = getSuccessResponse();

        return response;
    }

    @Override
    protected NewsDeleteApiResponse getResponse() {
        return new NewsDeleteApiResponse();
    }

}
