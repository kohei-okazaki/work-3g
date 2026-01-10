package jp.co.ha.business.db.crud.read;

import java.util.List;
import java.util.Optional;

import jp.co.ha.db.entity.RootLoginInfo;
import jp.co.ha.db.entity.custom.CompositeRootUserInfo;

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
    Optional<RootLoginInfo> findById(Long seqLoginId);

    /**
     * 指定したログインIDから管理者サイト複合ユーザ情報の一覧を検索する
     *
     * @param seqLoginId
     *     ログインID
     * @return 管理者サイト複合ユーザ情報の一覧
     */
    List<CompositeRootUserInfo> findCompositeUserById(Long seqLoginId);

}
