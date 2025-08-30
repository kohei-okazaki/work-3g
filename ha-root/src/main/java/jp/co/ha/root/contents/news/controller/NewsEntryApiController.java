package jp.co.ha.root.contents.news.controller;

import java.util.StringJoiner;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.db.crud.create.NewsInfoCreateService;
import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.component.NewsComponent;
import jp.co.ha.root.contents.news.request.NewsEntryApiRequest;
import jp.co.ha.root.contents.news.response.NewsEntryApiResponse;

/**
 * お知らせ情報登録APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsEntryApiController
        extends BaseRootApiController<NewsEntryApiRequest, NewsEntryApiResponse> {

    /** お知らせ情報登録サービス */
    @Autowired
    private NewsInfoCreateService createService;
    /** お知らせ情報Component */
    @Autowired
    private NewsComponent newsComponent;

    /**
     * お知らせ情報登録処理
     *
     * @param request
     *     お知らせ情報登録APIリクエスト
     * @return お知らせ情報登録APIレスポンス
     * @throws BaseException
     *     JSONの取得/アップロードまたはSlackの通知に失敗した場合
     */
    @PostMapping(value = "news", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<NewsEntryApiResponse> entry(
            @Valid @RequestBody NewsEntryApiRequest request) throws BaseException {

        // S3キーを取得
        String s3Key = getS3Key();
        NewsInfo news = new NewsInfo();
        news.setS3Key(s3Key);

        // おしらせ情報 登録
        createService.create(news);

        // お知らせ情報JSON アップロード
        NewsDto dto = new NewsDto();
        BeanUtil.copy(request, dto);
        dto.setSeqNewsInfoId(news.getSeqNewsInfoId());
        newsComponent.upload(s3Key, dto);

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NewsEntryApiResponse getResponse() {
        return new NewsEntryApiResponse();
    }

    /**
     * S3キーを返す
     *
     * @return S3キー
     */
    private String getS3Key() {
        return new StringJoiner(StringUtil.THRASH)
                .add("news")
                .add(DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDDHHMMSS_NOSEP) + FileExtension.JSON)
                .toString();
    }

}
