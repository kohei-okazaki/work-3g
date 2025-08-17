package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.UserRecoveryToken;

/**
 * ユーザ回復トークン検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface UserRecoveryTokenSearchService {

    /**
     * 以下の検索条件より、ユーザ回復トークンを検索する<br>
     * <ul>
     * <li>ユーザ回復トークン.ユーザID = 指定したユーザID</li>
     * <li>ユーザ回復トークン.トークン = 指定したトークン</li>
     * <li>ユーザ回復トークン.トークン作成日時 > システム日時 - 1時間</li>
     * </ul>
     *
     * @param seqUserId
     *     ユーザID
     * @param token
     *     トークン
     * @return ユーザ回復トークン
     */
    Optional<UserRecoveryToken> findBySeqUserIdAndTokenAndValidTokenCreateDate(
            Long seqUserId, String token);

}
