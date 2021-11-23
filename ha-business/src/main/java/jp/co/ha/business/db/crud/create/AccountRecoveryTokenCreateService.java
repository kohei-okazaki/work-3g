package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.AccountRecoveryTokenData;;

/**
 * アカウント回復トークン作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountRecoveryTokenCreateService {

    /**
     * アカウント回復トークンを登録する
     *
     * @param entity
     *     アカウント回復トークン
     */
    void create(AccountRecoveryTokenData entity);

}
