package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.ApiLog;

/**
 * API通信ログ検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface ApiLogSearchService {

    /**
     * API通信ログを検索する
     *
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return API通信ログのリスト
     */
    List<ApiLog> findAll(SelectOption selectOption);

    /**
     * 指定されたAPI通信ログIDのAPI通信ログの件数を取得する
     *
     * @param seqApiLogId
     *     API通信ログID
     * @return API通信ログの件数
     */
    long countBySeqApiLogId(Long seqApiLogId);
}
