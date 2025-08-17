package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.UserHealthGoal;

/**
 * ユーザ健康目標情報更新サービス
 * 
 * @version 1.0.0
 */
public interface UserHealthGoalUpdateService {

    /**
     * ユーザ健康目標情報を論理削除する
     * 
     * @param entity
     *     ユーザ健康目標情報
     */
    void updateDeleteFlag(UserHealthGoal entity);

}
