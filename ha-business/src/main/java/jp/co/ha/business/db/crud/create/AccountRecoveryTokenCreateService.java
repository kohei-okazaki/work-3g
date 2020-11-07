package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.AccountRecoveryToken;

/**
 * アカウント回復トークン作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountRecoveryTokenCreateService {

    /**
     * 指定したアカウント回復トークンを登録する
     *
     * @param entity
     *     アカウント回復トークン
     */
    void create(AccountRecoveryToken entity);

}
