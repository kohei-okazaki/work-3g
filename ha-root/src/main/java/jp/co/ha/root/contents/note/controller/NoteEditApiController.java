package jp.co.ha.root.contents.note.controller;

import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.business.db.crud.update.RootUserNoteInfoUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.note.request.NoteEditApiRequest;
import jp.co.ha.root.contents.note.response.NoteEditApiResponse;

/**
 * メモ編集APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NoteEditApiController
        extends BaseRootApiController<NoteEditApiRequest, NoteEditApiResponse> {

    /** 管理者サイトユーザメモ情報検索サービス */
    @Autowired
    private RootUserNoteInfoSearchService rootUserNoteInfoSearchService;
    /** 管理者サイトユーザメモ情報更新サービス */
    @Autowired
    private RootUserNoteInfoUpdateService rootUserNoteInfoUpdateService;
    /** AWS S3Component */
    @Autowired
    private AwsS3Component awsS3Component;
    /** SlackApiComponent */
    @Autowired
    private SlackApiComponent slackApi;

    /**
     * メモ情報編集
     *
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     * @param request
     *     メモ情報編集APIリクエスト
     * @return メモ情報編集APIレスポンス
     * @throws BaseException
     *     API呼び出しに失敗した場合
     */
    @PutMapping(value = "note/{seq_root_user_note_info_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<NoteEditApiResponse> edit(
            @PathVariable(name = "seq_root_user_note_info_id", required = true) Long seqRootUserNoteInfoId,
            @Valid @RequestBody NoteEditApiRequest request) throws BaseException {

        Optional<RootUserNoteInfo> optional = rootUserNoteInfoSearchService
                .findById(Long.valueOf(seqRootUserNoteInfoId));
        if (!optional.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(getErrorResponse("note_info is not found"));
        }

        // 管理者サイトユーザメモ情報更新
        rootUserNoteInfoUpdateService.update(Long.valueOf(seqRootUserNoteInfoId),
                request.getTitle());

        String s3Key = optional.get().getS3Key();
        // メモファイルの更新
        awsS3Component.putFile(s3Key, request.getDetail());
        // Slack通知
        slackApi.send(ContentType.ROOT,
                "メモ情報ID=" + seqRootUserNoteInfoId + ", S3キー=" + s3Key + "を編集.");

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NoteEditApiResponse getResponse() {
        return new NoteEditApiResponse();
    }

}
