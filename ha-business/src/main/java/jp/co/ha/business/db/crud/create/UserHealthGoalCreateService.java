package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.UserHealthGoal;

/**
 * ユーザ健康目標情報作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserHealthGoalCreateService {

    /**
     * ユーザ情報を登録する
     *
     * @param entity
     *     ユーザ情報
     */
    void create(UserHealthGoal entity);
}
