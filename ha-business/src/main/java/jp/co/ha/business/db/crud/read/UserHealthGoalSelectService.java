package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.UserHealthGoal;

/**
 * ユーザ健康目標情報サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserHealthGoalSelectService {

    /**
     * ユーザ健康目標情報を検索する
     *
     * @param seqUserId
     *     ユーザID
     * @return ユーザ健康目標情報
     */
    Optional<UserHealthGoal> findById(Long seqUserId);

}
