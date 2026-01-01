package jp.co.ha.root.contents.news.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.PagingView;
import jp.co.ha.db.entity.NewsInfo;
import jp.co.ha.root.contents.news.request.NewsEditApiRequest;
import jp.co.ha.root.contents.news.request.NewsEntryApiRequest;
import jp.co.ha.root.contents.news.response.NewsListApiResponse.News;

/**
 * お知らせ情報サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface NewsService {

    /**
     * 指定されたお知らせ情報IDと一致するお知らせ情報を返す
     * 
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @return お知らせ情報
     */
    Optional<NewsInfo> getNews(Long seqNewsInfoId);

    /**
     * お知らせ情報を削除する
     * 
     * @param newsInfo
     *     お知らせ情報
     * @throws BaseException
     *     S3処理に失敗したした場合
     */
    void deleteNews(Optional<NewsInfo> newsInfo) throws BaseException;

    /**
     * Slack通知を行う
     * 
     * @param seqNewsInfoId
     *     お知らせ情報ID
     */
    void noticeSlack(Long seqNewsInfoId);

    /**
     * お知らせ情報を更新する
     * 
     * @param newsInfo
     *     お知らせ情報
     * @param request
     *     おしらせ情報編集APIリクエスト
     * @throws BaseException
     *     S3処理に失敗した場合
     */
    void updateNews(Optional<NewsInfo> newsInfo, NewsEditApiRequest request)
            throws BaseException;

    /**
     * お知らせ情報を登録する
     * 
     * @param request
     *     おしらせ情報登録APIリクエスト
     * @throws BaseException
     *     お知らせ情報JSONのアップロードに失敗した場合
     */
    void createNews(NewsEntryApiRequest request) throws BaseException;

    /**
     * お知らせ情報リストを返す
     * 
     * @param pageable
     *     Pageable
     * @return お知らせ情報リスト
     * @throws BaseException
     *     S3処理に失敗した場合
     */
    List<News> getNewsList(Pageable pageable) throws BaseException;

    /**
     * PagingViewを返す
     * 
     * @param pageable
     *     Pageable
     * @return PagingView
     */
    PagingView getPagingView(Pageable pageable);
}
