package jp.co.ha.business.news.service;

import java.util.List;
import java.util.Optional;

import jp.co.ha.business.dto.NewsDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.db.entity.NewsInfo;

/**
 * お知らせ情報に関するService
 * 
 * @version 1.0.0
 */
public interface NewsService {

    /**
     * 指定されたS3キーよりお知らせ情報JSONを取得
     *
     * @param s3Key
     *     S3キー
     * @return お知らせ情報
     * @throws BaseException
     */
    NewsDto getNewsDto(String s3Key) throws BaseException;

    /**
     * お知らせ情報JSONをS3へファイルアップロード
     *
     * @param s3Key
     *     S3キー
     * @param dto
     *     お知らせ情報
     * @throws BaseException
     *     S3へのファイルアップロードに失敗した場合
     */
    void upload(String s3Key, NewsDto dto) throws BaseException;

    /**
     * 指定したS3キーを削除
     *
     * @param s3Key
     *     S3キー
     * @throws BusinessException
     *     S3ファイル削除失敗エラー
     */
    void remove(String s3Key) throws BusinessException;

    /**
     * Slackへお知らせ情報削除のメッセージを投稿する
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     */
    void sendSlack(Long seqNewsInfoId);

    /**
     * お知らせ情報を検索する
     *
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return お知らせ情報
     */
    List<NewsInfo> findAll(SelectOption selectOption);

    /**
     * お知らせ情報の件数を返す
     * 
     * @return 件数
     */
    long countBySeqNewsInfoId();

    /**
     * 指定したお知らせ情報IDのお知らせ情報を検索する
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @return お知らせ情報
     */
    Optional<NewsInfo> findById(Long seqNewsInfoId);

}
