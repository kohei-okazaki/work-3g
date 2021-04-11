package jp.co.ha.business.db.crud.read;

import java.util.List;

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
     * @return API通信情報のリスト
     */
    List<ApiCommunicationData> findAll();

    /**
     * トランザクションIDの最大値を取得する
     *
     * @return トランザクションIDの最大値
     */
    Long selectLastTransactionId();
}
