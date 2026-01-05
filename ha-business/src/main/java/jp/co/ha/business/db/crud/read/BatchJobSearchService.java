package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.custom.CustomJobData;

/**
 * Batch起動履歴検索サービスインターフェース
 * 
 * @version 1.0.0
 */
public interface BatchJobSearchService {

    /**
     * Batch起動履歴を検索する
     *
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return Batch起動履歴のリスト
     */
    List<CustomJobData> findAll(SelectOption selectOption);

}
