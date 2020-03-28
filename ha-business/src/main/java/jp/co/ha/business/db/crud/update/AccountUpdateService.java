package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.Account;

/**
 * アカウント情報更新サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountUpdateService {

    /**
     * 指定したアカウント情報を更新する
     *
     * @param entity
     *     アカウント情報
     */
    void update(Account entity);

}
