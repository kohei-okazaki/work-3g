package jp.co.ha.root.contents.news.controller;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsEditApiRequest;
import jp.co.ha.root.contents.news.response.NewsEditApiResponse;
import jp.co.ha.root.contents.news.service.NewsService;

/**
 * お知らせ情報編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsEditApiController
        extends BaseRootApiController<NewsEditApiRequest, NewsEditApiResponse> {

    /** お知らせ情報サービス */
    @Autowired
    private NewsService service;

    /**
     * 編集
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @param request
     *     おしらせ情報編集APIリクエスト
     * @return おしらせ情報編集APIレスポンス
     * @throws BaseException
     *     JSONの取得/アップロードまたはSlackの通知に失敗した場合
     */
    @PutMapping(value = "news/{seq_news_info_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsEditApiResponse> edit(
            @PathVariable(name = "seq_news_info_id", required = true) Long seqNewsInfoId,
            @Valid @RequestBody NewsEditApiRequest request) throws BaseException {

        // お知らせ情報を検索
        Optional<NewsInfo> optional = service.getNews(seqNewsInfoId);
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("news_info is not found"));
        }

        // お知らせ情報を更新する
        service.updateNews(optional, request);

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NewsEditApiResponse getResponse() {
        return new NewsEditApiResponse();
    }

}
