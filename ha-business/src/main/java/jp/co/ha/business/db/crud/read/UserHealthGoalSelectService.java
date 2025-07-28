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
     * 有効なユーザ健康目標情報を検索する<br>
     * <ul>
     * <li>seq_user_id = 引数値</li>
     * <li>delete_flag = false</li>
     * </ul>
     *
     * @param seqUserId
     *     ユーザID
     * @return ユーザ健康目標情報
     */
    Optional<UserHealthGoal> findEnableById(Long seqUserId);

}
