package jp.co.ha.root.contents.news.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ha.business.component.NewsComponent;
import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.validator.annotation.Decimal;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.root.base.BaseRootApiController;
import jp.co.ha.root.contents.news.request.NewsListApiRequest;
import jp.co.ha.root.contents.news.response.NewsListApiResponse;

/**
 * お知らせ情報一覧取得APIコントローラ
 *
 * @version 1.0.0
 */
@RestController
public class NewsListApiController
        extends BaseRootApiController<NewsListApiRequest, NewsListApiResponse> {

    /** お知らせ情報Component */
    @Autowired
    private NewsComponent component;

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

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_NEWS_INFO_ID", SortType.DESC)
                .pageable(pageable)
                .build();

        // おしらせ情報 取得
        List<NewsListApiResponse.News> newsResponseList = new ArrayList<>();
        for (NewsInfo entity : component.findAll(selectOption)) {
            NewsListApiResponse.News news = new NewsListApiResponse.News();
            news.setSeqNewsInfoId(entity.getSeqNewsInfoId());
            news.setRegDate(entity.getRegDate());
            news.setUpdateDate(entity.getUpdateDate());

            // S3からお知らせJSONを取得
            NewsDto dto = component.getNewsDto(entity.getS3Key());
            news.setTitle(dto.getTitle());
            news.setDate(dto.getDate());
            news.setDetail(dto.getDetail());

            NewsListApiResponse.Tag tag = new NewsListApiResponse.Tag();
            tag.setColor(dto.getTag().getColor());
            tag.setName(dto.getTag().getName());
            news.setTag(tag);
            newsResponseList.add(news);
        }

        PagingView paging = PagingViewFactory.getPageView(pageable, "news?page",
                component.count());

        NewsListApiResponse response = getSuccessResponse();
        response.setNewsList(newsResponseList);
        response.setPaging(paging);

        return ResponseEntity.ok(response);
    }

    @Override
    protected NewsListApiResponse getResponse() {
        return new NewsListApiResponse();
    }

}
