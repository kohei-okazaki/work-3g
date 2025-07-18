package jp.co.ha.root.contents.note.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.RootUserNoteInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.RootUserNoteInfoSearchServiceImpl;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
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

    /** {@linkplain RootUserNoteInfoSearchServiceImpl} */
    @Autowired
    private RootUserNoteInfoSearchService searchService;
    /** {@linkplain AwsS3Component} */
    @Autowired
    private AwsS3Component awsS3Component;

    /**
     * メモ一覧取得
     *
     * @param request
     *     メモ一覧取得APIリクエスト
     * @param seqLoginId
     *     ログインID
     * @param page
     *     ページ
     * @return メモ一覧取得APIレスポンス
     * @throws BaseException
     *     レスポンスの生成に失敗した場合
     */
    @GetMapping(value = "note", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<NoteListApiResponse> index(NoteListApiRequest request,
            @RequestParam(name = "seq_login_id", required = true) Long seqLoginId,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = 0, message = "page is positive") Integer page)
            throws BaseException {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getNotePage());

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_ROOT_USER_NOTE_INFO_ID", SortType.DESC)
                .pageable(pageable)
                .build();

        // 管理者サイトユーザメモ情報リストを取得
        List<RootUserNoteInfo> list = searchService
                .findBySeqLoginId(seqLoginId, selectOption);

        List<Note> noteList = new ArrayList<>();
        for (RootUserNoteInfo entity : list) {
            Note note = new Note();
            BeanUtil.copy(entity, note);
            note.setDetail(getDetail(entity.getS3Key()));
            noteList.add(note);
        }

        PagingView paging = PagingViewFactory.getPageView(pageable, "apidata?page",
                searchService.countBySeqLoginId(null));

        NoteListApiResponse response = getSuccessResponse();
        response.setNoteList(noteList);
        response.setPaging(paging);

        return ResponseEntity.ok(response);
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
