package jp.co.ha.root.contents.note.controller;

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

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.delete.RootUserNoteInfoDeleteService;
import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsDeleteApiRequest;
import jp.co.ha.root.contents.note.request.NoteDeleteApiRequest;
import jp.co.ha.root.contents.note.response.NoteDeleteApiResponse;

/**
 * メモ削除APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NoteDeleteApiController
        extends BaseRootApiController<NoteDeleteApiRequest, NoteDeleteApiResponse> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(NoteDeleteApiController.class);

    /** 管理者サイトユーザメモ情報検索サービス */
    @Autowired
    private RootUserNoteInfoSearchService rootUserNoteInfoSearchService;
    /** 理者サイトユーザメモ情報削除サービス */
    @Autowired
    private RootUserNoteInfoDeleteService rootUserNoteInfoDeleteService;
    /** AWS S3Component */
    @Autowired
    private AwsS3Component s3;
    /** SlackApiComponent */
    @Autowired
    private SlackApiComponent slack;
    /** トランザクション管理クラス */
    @Autowired
    private PlatformTransactionManager transactionManager;
    /** トランザクション定義 */
    @Autowired
    @Qualifier("transactionDefinition")
    private DefaultTransactionDefinition defaultTransactionDefinition;

    /**
     * メモ情報削除処理
     *
     * @param request
     *     メモ情報削除APIリクエスト
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     * @return メモ情報削除APIレスポンス
     * @throws BaseException
     */
    @DeleteMapping(value = "note/{seq_root_user_note_info_id}")
    public ResponseEntity<NoteDeleteApiResponse> delete(NewsDeleteApiRequest request,
            @PathVariable(name = "seq_root_user_note_info_id", required = true) Long seqRootUserNoteInfoId)
            throws BaseException {

        // 管理者サイトユーザメモ情報を検索
        Optional<RootUserNoteInfo> optional = rootUserNoteInfoSearchService
                .findById(Long.valueOf(seqRootUserNoteInfoId));
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("note_info is not found"));
        }

        // トランザクション開始
        TransactionStatus status = transactionManager
                .getTransaction(defaultTransactionDefinition);

        try {
            String s3Key = optional.get().getS3Key();

            // メモ情報を削除
            rootUserNoteInfoDeleteService.delete(seqRootUserNoteInfoId);

            // メモファイルを削除
            s3.removeS3ObjectByKeys(s3Key);

            // メモファイルまで削除できたらコミット
            transactionManager.commit(status);

            // Slack通知
            slack.send(ContentType.ROOT,
                    "メモ情報ID=" + seqRootUserNoteInfoId + ", S3キー=" + s3Key + "を削除.");

        } catch (Exception e) {

            // DELETE処理中にエラーが起きた場合、ロールバック
            transactionManager.rollback(status);

            LOG.error("メモ情報の削除に失敗しました", e);
            slack.sendError(ContentType.ROOT, e);

            // エラーレスポンスを返却
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("note_info delete error"));
        }

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NoteDeleteApiResponse getResponse() {
        return new NoteDeleteApiResponse();
    }

}
