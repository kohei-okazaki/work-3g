package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.RootLoginInfo;

/**
 * 管理者サイトユーザログイン情報検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootLoginInfoSearchService {

    /**
     * 指定したログインIDから管理者サイトユーザログイン情報を検索する
     *
     * @param seqLoginId
     *     ログインID
     * @return RootLoginInfo
     */
    Optional<RootLoginInfo> findById(Integer seqLoginId);

}
