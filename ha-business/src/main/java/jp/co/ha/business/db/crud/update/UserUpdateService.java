package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.User;

/**
 * ユーザ情報更新サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserUpdateService {

    /**
     * 指定したユーザ情報を更新する
     *
     * @param entity
     *     ユーザ情報
     */
    void update(User entity);

    /**
     * 指定したユーザ情報を更新する<br>
     * null項目は更新しない
     *
     * @param entity
     *     ユーザ情報
     */
    void updateSelective(User entity);

}
