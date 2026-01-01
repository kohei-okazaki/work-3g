package jp.co.ha.root.contents.news.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.component.NewsComponent;
import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.root.base.BaseRootApiController;
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

    /** お知らせ情報Component */
    @Autowired
    private NewsComponent component;

    /**
     * 登録
     *
     * @param request
     *     お知らせ情報登録APIリクエスト
     * @return お知らせ情報登録APIレスポンス
     * @throws BaseException
     *     JSONの取得/アップロードまたはSlackの通知に失敗した場合
     */
    @PostMapping(value = "news", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsEntryApiResponse> entry(
            @Valid @RequestBody NewsEntryApiRequest request) throws BaseException {

        NewsDto dto = new NewsDto();
        BeanUtil.copy(request, dto);

        NewsDto.Tag tag = new NewsDto.Tag();
        BeanUtil.copy(request.getTag(), tag);
        dto.setTag(tag);

        // お知らせ情報の登録とJSONアップロード
        component.createNews(dto);

        return ResponseEntity.ok(getSuccessResponse());
    }

    @Override
    protected NewsEntryApiResponse getResponse() {
        return new NewsEntryApiResponse();
    }

}
