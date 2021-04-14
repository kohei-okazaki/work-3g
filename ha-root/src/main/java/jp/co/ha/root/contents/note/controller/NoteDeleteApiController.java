package jp.co.ha.root.contents.note.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.exception.BaseException;
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

        NoteDeleteApiResponse response = getSuccessResponse();
        return response;
    }

    @Override
    protected NoteDeleteApiResponse getResponse() {
        return new NoteDeleteApiResponse();
    }

}
