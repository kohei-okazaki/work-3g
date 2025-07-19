package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.User;

/**
 * ユーザ作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserCreateService {

    /**
     * ユーザ情報を登録する
     *
     * @param entity
     *     ユーザ情報
     */
    void create(User entity);
}
