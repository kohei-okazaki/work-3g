package jp.co.ha.business.db.crud.read;

import java.util.List;
import java.util.Optional;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.NewsInfo;

/**
 * お知らせ情報検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface NewsInfoSearchService {

    /**
     * お知らせ情報を検索する
     *
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return お知らせ情報
     */
    List<NewsInfo> findAll(SelectOption selectOption);

    /**
     * 指定されたお知らせ情報IDのお知らせ情報を件数を返す
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @return お知らせ情報を件数
     */
    long countBySeqNewsInfoId(Long seqNewsInfoId);

    /**
     * 指定したお知らせ情報IDのお知らせ情報を検索する
     *
     * @param seqNewsInfoId
     *     お知らせ情報ID
     * @return お知らせ情報
     */
    Optional<NewsInfo> findById(Long seqNewsInfoId);

}
