package jp.co.ha.root.contents.news.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.news.service.NewsService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.root.base.BaseRootApiController;
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

    /** お知らせ情報サービス */
    @Autowired
    private NewsService newsService;

    /**
     * 削除
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @param request
     *     おしらせ情報削除APIリクエスト
     * @return おしらせ情報削除APIレスポンス
     * @throws BaseException
     *     URLが不正な場合
     */
    @DeleteMapping(value = "news/{seq_news_info_id}")
    public ResponseEntity<NewsDeleteApiResponse> delete(
            @PathVariable(name = "seq_news_info_id", required = true) Long seqNewsInfoId,
            NewsDeleteApiRequest request) throws BaseException {

        // お知らせ情報を検索
        Optional<NewsInfo> optional = newsService.findById(seqNewsInfoId);
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("news_info is not found"));
        }

        // お知らせ情報を論理削除
        newsService.updateLongicalDelete(optional.get());

        // Slack通知
        newsService.sendSlack(seqNewsInfoId);

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NewsDeleteApiResponse getResponse() {
        return new NewsDeleteApiResponse();
    }

}
