package jp.co.ha.root.contents.note.controller;

import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.db.crud.create.RootUserNoteInfoCreateService;
import jp.co.ha.business.db.crud.create.impl.RootUserNoteInfoCreateServiceImpl;
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

    /** {@linkplain RootUserNoteInfoCreateServiceImpl} */
    @Autowired
    private RootUserNoteInfoCreateService rootUserNoteInfoCreateService;
    /** {@linkplain AwsS3Component} */
    @Autowired
    private AwsS3Component s3Component;
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApi;

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
    public NoteEntryApiResponse entry(@RequestBody NoteEntryApiRequest request)
            throws BaseException {

        // S3キーを取得
        String s3Key = getS3Key(request.getSeqRootLoginInfoId());
        s3Component.putFile(s3Key, request.getDetail());

        RootUserNoteInfo entity = new RootUserNoteInfo();
        entity.setS3Key(s3Key);
        BeanUtil.copy(request, entity);

        // 管理者サイトユーザメモ情報を登録
        rootUserNoteInfoCreateService.create(entity);
        // Slack通知
        slackApi.send(ContentType.ROOT, "メモ情報ID=" + entity.getSeqRootUserNoteInfoId()
                + ", S3キー=" + s3Key + "を登録.");

        NoteEntryApiResponse response = getSuccessResponse();
        return response;
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
                .add("note")
                .add(seqRootLoginInfoId.toString())
                .add(DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDDHHMMSS_NOSEP) + FileExtension.TEXT)
                .toString();
    }

}
