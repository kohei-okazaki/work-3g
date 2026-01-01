package jp.co.ha.root.contents.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsListApiRequest;
import jp.co.ha.root.contents.news.response.NewsListApiResponse;
import jp.co.ha.root.contents.news.service.NewsService;

/**
 * お知らせ情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsListApiController
        extends BaseRootApiController<NewsListApiRequest, NewsListApiResponse> {

    /** お知らせ情報サービス */
    @Autowired
    private NewsService service;

    /**
     * 一覧取得
     *
     * @param request
     *     お知らせ情報一覧取得APIリクエスト
     * @param page
     *     取得対象ページ
     * @return お知らせ情報一覧取得APIレスポンス
     * @throws BaseException
     *     S3よりお知らせ情報JSONを取得できなかった場合
     */
    @GetMapping(value = "news")
    public ResponseEntity<NewsListApiResponse> index(NewsListApiRequest request,
            @RequestParam(name = "page", required = true, defaultValue = "0") @Decimal(min = "0", message = "page is positive") Integer page)
            throws BaseException {

        // ページング情報を取得(1ページあたりの表示件数はapplication-${env}.ymlより取得)
        Pageable pageable = PagingViewFactory.getPageable(page,
                applicationProperties.getNewsPage());

        NewsListApiResponse response = getSuccessResponse();
        response.setNewsList(service.getNewsList(pageable));
        response.setPaging(service.getPagingView(pageable));

        return ResponseEntity.ok(response);
    }

    @Override
    protected NewsListApiResponse getResponse() {
        return new NewsListApiResponse();
    }

}
