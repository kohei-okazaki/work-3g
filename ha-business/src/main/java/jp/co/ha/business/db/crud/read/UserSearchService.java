package jp.co.ha.business.db.crud.read;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import jp.co.ha.common.db.SelectOption;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.composite.CompositeMonthlyRegData;
import jp.co.ha.db.entity.composite.CompositeUser;

/**
 * ユーザ検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserSearchService {

    /**
     * ユーザIDからユーザ情報を取得する
     *
     * @param seqUserId
     *     ユーザID
     * @return ユーザ情報
     */
    Optional<User> findById(Long seqUserId);

    /**
     * ユーザIDからユーザ情報と健康情報ファイル設定の複合Entityを取得する
     *
     * @param seqUserId
     *     ユーザID
     * @return ユーザ情報とメール情報と健康情報ファイル設定の複合Entity
     */
    Optional<CompositeUser> findCompositUserById(Long seqUserId);

    /**
     * メールアドレスからユーザ情報を取得する
     *
     * @param mailAddress
     *     メールアドレス
     * @return ユーザ情報
     */
    Optional<User> findByMailAddress(String mailAddress);

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
     * ユーザ情報と健康情報ファイル設定の複合Entityのリストを検索する
     *
     * @param selectOption
     *     {@linkplain SelectOption}
     * @return ユーザ情報と健康情報ファイル設定の複合Entityのリスト
     */
    List<CompositeUser> findAll(SelectOption selectOption);

    /**
     * 月ごとの登録情報リストを返す
     *
     * @param from
     *     登録日時(開始)
     * @param to
     *     登録日時(終了)
     * @return 月ごとの登録情報リスト
     */
    List<CompositeMonthlyRegData> findMonthly(LocalDateTime from, LocalDateTime to);

    /**
     * 指定されたユーザIDと一致するユーザ情報の件数を返す
     * 
     * @param seqUserId
     *     ユーザID
     * @return ユーザ情報の件数
     */
    long countBySeqUserId(Long seqUserId);

}
