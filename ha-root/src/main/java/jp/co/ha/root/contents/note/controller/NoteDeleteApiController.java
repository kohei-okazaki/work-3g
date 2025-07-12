package jp.co.ha.root.contents.note.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.delete.RootUserNoteInfoDeleteService;
import jp.co.ha.business.db.crud.delete.impl.RootUserNoteInfoDeleteServiceImpl;
import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.RootUserNoteInfoSearchServiceImpl;
import jp.co.ha.common.exception.BaseException;
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

    /** {@linkplain RootUserNoteInfoSearchServiceImpl} */
    @Autowired
    private RootUserNoteInfoSearchService rootUserNoteInfoSearchService;
    /** {@linkplain RootUserNoteInfoDeleteServiceImpl} */
    @Autowired
    private RootUserNoteInfoDeleteService rootUserNoteInfoDeleteService;
    /** {@linkplain AwsS3Component} */
    @Autowired
    private AwsS3Component awsS3Component;
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApi;

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
    @DeleteMapping(value = "note/{seq_root_user_note_info_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<NoteDeleteApiResponse> delete(NewsDeleteApiRequest request,
            @PathVariable(name = "seq_root_user_note_info_id", required = true) Long seqRootUserNoteInfoId)
            throws BaseException {

        // 管理者サイトユーザメモ情報を検索
        Optional<RootUserNoteInfo> optional = rootUserNoteInfoSearchService
                .findById(Long.valueOf(seqRootUserNoteInfoId));
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("news_info is not found"));
        }

        String s3Key = optional.get().getS3Key();

        // メモ情報を削除
        rootUserNoteInfoDeleteService.delete(Long.valueOf(seqRootUserNoteInfoId));
        // メモファイルを削除
        awsS3Component.removeS3ObjectByKeys(s3Key);
        // Slack通知
        slackApi.send(ContentType.ROOT,
                "メモ情報ID=" + seqRootUserNoteInfoId + ", S3キー=" + s3Key + "を削除.");

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NoteDeleteApiResponse getResponse() {
        return new NoteDeleteApiResponse();
    }

}
