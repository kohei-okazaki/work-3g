package jp.co.ha.root.contents.news.service;

import static jp.co.ha.common.db.SelectOption.SortType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.slack.SlackApiComponent;
import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;
import jp.co.ha.business.component.NewsComponent;
import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.root.contents.news.request.NewsEditApiRequest;
import jp.co.ha.root.contents.news.request.NewsEntryApiRequest;
import jp.co.ha.root.contents.news.response.NewsListApiResponse;
import jp.co.ha.root.contents.news.response.NewsListApiResponse.News;

/**
 * お知らせ情報サービス実装クラス
 * 
 * @version 1.0.0
 */
@Service
public class NewsServiceImpl implements NewsService {

    /** お知らせ情報Component */
    @Autowired
    private NewsComponent component;
    /** SlackApiComponent */
    @Autowired
    private SlackApiComponent slack;

    @Override
    public Optional<NewsInfo> getNews(Long seqNewsInfoId) {
        return component.findById(seqNewsInfoId);
    }

    @Override
    public void deleteNews(Optional<NewsInfo> optional) throws BaseException {

        // お知らせ情報を論理削除
        component.updateLongicalDelete(optional.get());

        // お知らせ情報S3を削除
        component.remove(optional.get().getS3Key());
    }

    @Override
    public void noticeSlack(Long seqNewsInfoId) {
        slack.send(ContentType.ROOT, "お知らせ情報ID=" + seqNewsInfoId + "を削除.");
    }

    @Override
    public void updateNews(Optional<NewsInfo> newsInfo, NewsEditApiRequest request)
            throws BaseException {

        // お知らせ情報を取得
        NewsDto dto = component.getNewsDto(newsInfo.get().getS3Key());
        BeanUtil.copy(request, dto);

        // お知らせ情報を更新
        component.updateNews(dto, newsInfo.get().getS3Key());
    }

    @Override
    public void createNews(NewsEntryApiRequest request) throws BaseException {

        NewsDto dto = new NewsDto();
        BeanUtil.copy(request, dto);

        NewsDto.Tag tag = new NewsDto.Tag();
        BeanUtil.copy(request.getTag(), tag);
        dto.setTag(tag);

        // お知らせ情報の登録とJSONアップロード
        component.createNews(dto);
    }

    @Override
    public List<News> getNewsList(Pageable pageable) throws BaseException {

        SelectOption selectOption = new SelectOptionBuilder()
                .orderBy("SEQ_NEWS_INFO_ID", DESC)
                .pageable(pageable)
                .build();

        List<News> list = new ArrayList<>();

        // おしらせ情報 取得
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
            list.add(news);
        }

        return list;
    }

    @Override
    public PagingView getPagingView(Pageable pageable) {
        return PagingViewFactory.getPageView(pageable, "news?page", component.count());
    }

}
