package jp.co.ha.business.db.crud.read;

import java.util.List;
import java.util.Optional;

import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.composite.CompositeAccount;

/**
 * アカウント検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountSearchService {

    /**
     * ユーザIDからアカウント情報を取得する
     *
     * @param seqUserId
     *     ユーザID
     * @return アカウント情報
     */
    Optional<Account> findById(Integer seqUserId);

    /**
     * ユーザIDからアカウント情報と健康情報ファイル設定の複合Entityを取得する
     *
     * @param seqUserId
     *     ユーザID
     * @return アカウント情報とメール情報と健康情報ファイル設定の複合Entity
     */
    Optional<CompositeAccount> findCompositAccountById(Integer seqUserId);

    /**
     * メールアドレスからアカウント情報を取得する
     *
     * @param mailAddress
     *     メールアドレス
     * @return アカウント情報
     */
    Optional<Account> findByMailAddress(String mailAddress);

    /**
     * 指定したメールアドレスが存在するかどうか判定する<br>
     * <ul>
     * <li>存在する場合、true</li>
     * <li>存在しない場合、false</li>
     * </ul>
     *
     * @param mailAddress
     *     メールアドレス
     * @return 判定結果
     */
    boolean isExistByMailAddress(String mailAddress);

    /**
     * アカウント情報と健康情報ファイル設定の複合Entityのリストを検索する
     *
     * @return アカウント情報と健康情報ファイル設定の複合Entityのリスト
     */
    List<CompositeAccount> findAll();

}
