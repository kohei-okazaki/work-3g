package jp.co.ha.root.contents.news.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.delete.NewsInfoDeleteService;
import jp.co.ha.business.db.crud.read.NewsInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
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

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(NewsDeleteApiController.class);

    /** お知らせ情報検索サービス */
    @Autowired
    private NewsInfoSearchService searchService;
    /** お知らせ情報削除サービス */
    @Autowired
    private NewsInfoDeleteService deleteService;
    /** お知らせ情報Component */
    @Autowired
    private NewsComponent newsComponent;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクション定義 */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /**
     * お知らせ情報削除
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
        Optional<NewsInfo> optional = searchService.findById(seqNewsInfoId);
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("news_info is not found"));
        }

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {
            // お知らせ情報を削除
            deleteService.delete(seqNewsInfoId);

            // お知らせ情報JSONを削除
            newsComponent.remove(optional.get().getS3Key());

            // Slack通知
            newsComponent.sendSlack(seqNewsInfoId);

        } catch (Exception e) {
            LOG.error("お知らせ情報の削除に失敗しました", e);

            // DELETE処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);

            // エラーレスポンスを返却
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("news_info delete error"));
        }

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NewsDeleteApiResponse getResponse() {
        return new NewsDeleteApiResponse();
    }

}
