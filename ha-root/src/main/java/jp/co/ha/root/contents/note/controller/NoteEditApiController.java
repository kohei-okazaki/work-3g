package jp.co.ha.root.contents.note.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.business.db.crud.update.RootUserNoteInfoUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.note.request.NoteEditApiRequest;
import jp.co.ha.root.contents.note.response.NoteEditApiResponse;

/**
 * メモ情報編集APIコントローラ
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
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component awsS3Component;

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
    public NoteEditApiResponse edit(
            @PathVariable(name = "seq_root_user_note_info_id", required = false) Optional<String> seqRootUserNoteInfoId,
            @RequestBody NoteEditApiRequest request) throws BaseException {

        if (!seqRootUserNoteInfoId.isPresent()) {
            // URLが不正の場合
            return getErrorResponse("seq_root_user_note_info_id is required");
        }

        // 管理者サイトユーザメモ情報更新
        rootUserNoteInfoUpdateService.update(Long.valueOf(seqRootUserNoteInfoId.get()),
                request.getTitle());

        Optional<RootUserNoteInfo> optional = rootUserNoteInfoSearchService
                .findById(Long.valueOf(seqRootUserNoteInfoId.get()));
        if (!optional.isPresent()) {
            return getErrorResponse("note_info is not found");
        }
        // メモファイルの更新
        awsS3Component.putFile(optional.get().getS3Key(), request.getDetail());

        NoteEditApiResponse response = getSuccessResponse();
        return response;
    }

    @Override
    protected NoteEditApiResponse getResponse() {
        return new NoteEditApiResponse();
    }

}
