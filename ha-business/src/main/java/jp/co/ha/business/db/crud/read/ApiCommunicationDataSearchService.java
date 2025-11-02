package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * API通信情報検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface ApiCommunicationDataSearchService {

    /**
     * API通信情報を検索する
     *
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return API通信情報のリスト
     */
    List<ApiCommunicationData> findAll(SelectOption selectOption);

    /**
     * 指定されたAPI通信情報IDのAPI通信情報の件数を取得する
     *
     * @param seqApiCommunicationDataId
     *     API通信情報ID
     * @return API通信情報の件数
     */
    long countBySeqApiCommunicationDataId(Long seqApiCommunicationDataId);
}
