package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.ApiCommunicationData;

/**
 * API通信情報作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface ApiCommunicationDataCreateService {

    /**
     * 指定したAPI通信情報を登録する
     *
     * @param entity
     *     API通信情報
     */
    void create(ApiCommunicationData entity);

}
