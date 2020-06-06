package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.composit.CompositAccount;

/**
 * アカウント検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountSearchService {

    /**
     * ユーザIDからアカウント情報を取得する
     *
     * @param userId
     *     ユーザID
     * @return アカウント情報
     */
    Optional<Account> findById(String userId);

    /**
     * ユーザIDからアカウント情報とメール情報と健康情報ファイル設定の複合Entityを取得する
     * 
     * @param userId
     *     ユーザID
     * @return アカウント情報とメール情報と健康情報ファイル設定の複合Entity
     */
    Optional<CompositAccount> findCompositAccountById(String userId);

}
