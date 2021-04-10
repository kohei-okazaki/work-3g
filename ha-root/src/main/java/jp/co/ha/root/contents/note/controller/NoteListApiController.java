package jp.co.ha.root.contents.note.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.note.request.NoteListApiRequest;
import jp.co.ha.root.contents.note.response.NoteListApiResponse;

/**
 * メモ一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NoteListApiController
        extends BaseRootApiController<NoteListApiRequest, NoteListApiResponse> {

    /**
     * メモ一覧取得
     *
     * @param request
     *     メモ一覧取得APIリクエスト
     * @return メモ一覧取得APIレスポンス
     * @throws BaseException
     *     レスポンスの生成に失敗した場合
     */
    @GetMapping(value = "note", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NoteListApiResponse index(NoteListApiRequest request) throws BaseException {

        NoteListApiResponse response = getSuccessResponse();
        return response;
    }

    @Override
    protected NoteListApiResponse getResponse() {
        return new NoteListApiResponse();
    }

}
