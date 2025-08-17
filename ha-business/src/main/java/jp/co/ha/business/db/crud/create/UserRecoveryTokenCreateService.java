package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.UserRecoveryToken;;

/**
 * ユーザ回復トークン作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserRecoveryTokenCreateService {

    /**
     * ユーザ回復トークンを登録する
     *
     * @param entity
     *     ユーザ回復トークン
     */
    void create(UserRecoveryToken entity);

}
