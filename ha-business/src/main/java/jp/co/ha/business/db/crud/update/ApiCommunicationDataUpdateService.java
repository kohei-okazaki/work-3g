package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * API通信情報更新サービスインターフェース
 *
 * @version 1.0.0
 */
public interface ApiCommunicationDataUpdateService {

    /**
     * 指定したAPI通信情報を更新する
     *
     * @param entity
     *     API通信情報
     */
    void update(ApiCommunicationData entity);

}
