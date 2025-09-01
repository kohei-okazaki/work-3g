package jp.co.ha.root.contents.note.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.business.db.crud.update.RootUserNoteInfoUpdateService;
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

    /** 管理者サイトユーザメモ情報検索サービス */
    @Autowired
    private RootUserNoteInfoSearchService searchService;
    /** 理者サイトユーザメモ情報更新サービス */
    @Autowired
    private RootUserNoteInfoUpdateService updateService;

    /**
     * メモ情報削除処理
     *
     * @param request
     *     メモ情報削除APIリクエスト
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     * @return メモ情報削除APIレスポンス
     */
    @DeleteMapping(value = "note/{seq_root_user_note_info_id}")
    public ResponseEntity<NoteDeleteApiResponse> delete(NewsDeleteApiRequest request,
            @PathVariable(name = "seq_root_user_note_info_id", required = true) Long seqRootUserNoteInfoId) {

        // 管理者サイトユーザメモ情報を検索
        Optional<RootUserNoteInfo> optional = searchService
                .findById(Long.valueOf(seqRootUserNoteInfoId));
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("note_info is not found"));
        }

        // メモ情報を論理削除
        RootUserNoteInfo entity = optional.get();
        entity.setDeleteFlag(true);
        updateService.updateById(entity);

        // Slack通知
        slack.send(ContentType.ROOT,
                "メモ情報ID=" + seqRootUserNoteInfoId + ", S3キー=" + entity.getS3Key()
                        + "を削除.");

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NoteDeleteApiResponse getResponse() {
        return new NoteDeleteApiResponse();
    }

}
