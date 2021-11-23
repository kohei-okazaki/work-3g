package jp.co.ha.root.contents.note.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
 * メモ情報削除APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NoteDeleteApiController
        extends BaseRootApiController<NoteDeleteApiRequest, NoteDeleteApiResponse> {

    /** {@linkplain RootUserNoteInfoSearchServiceImpl} */
    @Autowired
    private RootUserNoteInfoSearchService searchService;
    /** {@linkplain RootUserNoteInfoDeleteServiceImpl} */
    @Autowired
    private RootUserNoteInfoDeleteService deleteService;
    /** {@linkplain AwsS3Component} */
    @Autowired
    private AwsS3Component awsS3Component;
    /** {@linkplain SlackApiComponent} */
    @Autowired
    private SlackApiComponent slackApi;

    /**
     * メモ情報削除処理
     *
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     * @param request
     *     メモ情報削除APIリクエスト
     * @return メモ情報削除APIレスポンス
     * @throws BaseException
     */
    @DeleteMapping(value = "note/{seq_root_user_note_info_id}", produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public NoteDeleteApiResponse delete(
            @PathVariable(name = "seq_root_user_note_info_id", required = false) Optional<String> seqRootUserNoteInfoId,
            NewsDeleteApiRequest request) throws BaseException {

        if (!seqRootUserNoteInfoId.isPresent()) {
            // URLが不正の場合
            return getErrorResponse("seq_root_user_note_info_id is required");
        }

        // 管理者サイトユーザメモ情報を検索
        Optional<RootUserNoteInfo> optional = searchService
                .findById(Long.valueOf(seqRootUserNoteInfoId.get()));
        if (!optional.isPresent()) {
            return getErrorResponse("note_info is not found");
        }

        // メモ情報を削除
        deleteService.delete(Long.valueOf(seqRootUserNoteInfoId.get()));
        // メモファイルを削除
        awsS3Component.removeS3ObjectByKeys(optional.get().getS3Key());
        // Slack通知
        slackApi.send(ContentType.ROOT,
                "メモ情報ID=" + seqRootUserNoteInfoId.get() + "を削除しました");

        NoteDeleteApiResponse response = getSuccessResponse();
        return response;
    }

    @Override
    protected NoteDeleteApiResponse getResponse() {
        return new NoteDeleteApiResponse();
    }

}
