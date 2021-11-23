package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.Account;

/**
 * アカウント作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountCreateService {

    /**
     * アカウント情報を登録する
     *
     * @param entity
     *     アカウント情報
     */
    void create(Account entity);
}
