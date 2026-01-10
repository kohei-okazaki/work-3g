package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.ApiLog;

/**
 * API通信ログ作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface ApiLogCreateService {

    /**
     * API通信ログを登録する
     *
     * @param entity
     *     API通信ログ
     */
    void create(ApiLog entity);

}
