package jp.co.ha.root.contents.note.controller;

import java.util.StringJoiner;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.create.RootUserNoteInfoCreateService;
import jp.co.ha.common.aws.AwsS3Component;
import jp.co.ha.common.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.note.request.NoteEntryApiRequest;
import jp.co.ha.root.contents.note.response.NoteEntryApiResponse;

/**
 * メモ登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NoteEntryApiController
        extends BaseRootApiController<NoteEntryApiRequest, NoteEntryApiResponse> {

    /** 管理者サイトユーザメモ情報登録サービス */
    @Autowired
    private RootUserNoteInfoCreateService rootUserNoteInfoCreateService;
    /** AWS S3Component */
    @Autowired
    private AwsS3Component s3;

    /**
     * メモ登録処理
     *
     * @param request
     *     メモ登録APIリクエスト
     * @return メモ登録APIレスポンス
     * @throws BaseException
     *     メモ登録に失敗した場合
     */
    @PostMapping(value = "note", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<NoteEntryApiResponse> entry(
            @Valid @RequestBody NoteEntryApiRequest request) throws BaseException {

        // S3キーを取得
        String s3Key = getS3Key(request.getSeqRootLoginInfoId());
        s3.putFile(s3Key, request.getDetail());

        RootUserNoteInfo entity = new RootUserNoteInfo();
        entity.setS3Key(s3Key);
        entity.setDeleteFlag(false);
        BeanUtil.copy(request, entity);

        // 管理者サイトユーザメモ情報を登録
        rootUserNoteInfoCreateService.create(entity);
        // Slack通知
        slack.sendFile(ContentType.ROOT, request.getDetail().getBytes(), s3Key,
                "メモ登録", "メモ情報ID=" + entity.getSeqRootUserNoteInfoId() + ", S3キー=" + s3Key
                        + "を登録.");

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NoteEntryApiResponse getResponse() {
        return new NoteEntryApiResponse();
    }

    /**
     * 指定した管理者サイトログイン情報IDからS3キーを返す
     *
     * @param seqRootLoginInfoId
     *     管理者サイトログイン情報ID
     * @return S3キー
     */
    private String getS3Key(Long seqRootLoginInfoId) {
        return new StringJoiner(StringUtil.THRASH)
                .add(AwsS3Key.NOTE_FILE.getValue())
                .add(seqRootLoginInfoId.toString())
                .add(DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDDHHMMSS_NOSEP) + FileExtension.TEXT)
                .toString();
    }

}
