package jp.co.ha.root.contents.note.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.RootUserNoteInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.note.request.NoteListApiRequest;
import jp.co.ha.root.contents.note.response.NoteListApiResponse;
import jp.co.ha.root.contents.note.response.NoteListApiResponse.Note;

/**
 * メモ一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NoteListApiController
        extends BaseRootApiController<NoteListApiRequest, NoteListApiResponse> {

    /** 管理者サイトユーザメモ情報検索サービス */
    @Autowired
    private RootUserNoteInfoSearchService rootUserNoteInfoSearchService;
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component awsS3Component;

    /**
     * メモ一覧取得
     *
     * @param request
     *     メモ一覧取得APIリクエスト
     * @param seqLoginId
     *     ログインID
     * @return メモ一覧取得APIレスポンス
     * @throws BaseException
     *     レスポンスの生成に失敗した場合
     */
    @GetMapping(value = "note", produces = { MediaType.APPLICATION_JSON_VALUE })
    public NoteListApiResponse index(NoteListApiRequest request,
            @RequestParam(name = "seq_login_id", required = false) Optional<Long> seqLoginId)
            throws BaseException {

        if (seqLoginId == null || !seqLoginId.isPresent()) {
            return getErrorResponse("seq_login_id is required");
        }

        // 管理者サイトユーザメモ情報リストを取得
        List<RootUserNoteInfo> list = rootUserNoteInfoSearchService
                .findBySeqLoginId(seqLoginId.get());

        NoteListApiResponse response = getSuccessResponse();

        List<Note> noteList = new ArrayList<>();
        for (RootUserNoteInfo entity : list) {
            Note note = new Note();
            BeanUtil.copy(entity, note);
            note.setDetail(getDetail(entity.getS3Key()));
            noteList.add(note);
        }
        response.setNoteList(noteList);

        return response;
    }

    @Override
    protected NoteListApiResponse getResponse() {
        return new NoteListApiResponse();
    }

    /**
     * S3キーより、メモ内容を返す
     *
     * @param s3Key
     *     S3キー
     * @return メモ内容
     * @throws BaseException
     *     メモ内容の取得に失敗した場合
     */
    private String getDetail(String s3Key) throws BaseException {

        try (InputStream is = awsS3Component.getS3ObjectByKey(s3Key);
                Reader r = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(r)) {

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            throw new BusinessException(e);
        }
    }

}
