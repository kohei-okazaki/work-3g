package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.Account;

/**
 * アカウント検索サービスインターフェース
 *
 * @since 1.0
 */
public interface AccountSearchService {

    /**
     * ユーザIDからアカウント情報を取得する
     *
     * @param userId
     *     ユーザID
     * @return アカウント情報
     */
    Optional<Account> findByUserId(String userId);

}
