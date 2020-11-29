package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.AccountRecoveryTokenData;

/**
 * アカウント回復トークン検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountRecoveryTokenSearchService {

    /**
     * 以下の検索条件より、アカウント回復トークンを検索する<br>
     * <ul>
     * <li>アカウント回復トークン.ユーザID = 指定したユーザID</li>
     * <li>アカウント回復トークン.トークン = 指定したトークン</li>
     * <li>アカウント回復トークン.トークン作成日時 > システム日時 - 1時間</li>
     * </ul>
     *
     * @param seqUserId
     *     ユーザID
     * @param token
     *     トークン
     * @return アカウント回復トークン
     */
    Optional<AccountRecoveryTokenData> findBySeqUserIdAndTokenAndValidTokenCreateDate(
            Integer seqUserId, String token);

}
