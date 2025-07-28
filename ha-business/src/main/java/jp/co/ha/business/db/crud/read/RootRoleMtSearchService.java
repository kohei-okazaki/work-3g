package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.db.entity.RootRoleMt;

/**
 * 管理者サイト権限マスタ検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootRoleMtSearchService {

    /**
     * 指定した権限の管理者サイト権限マスタを検索する
     *
     * @param roles
     *     権限
     * @return 管理者サイト権限マスタリスト
     */
    List<RootRoleMt> findByRoles(List<String> roles);

    /**
     * 管理者サイト権限マスタを全件検索する
     *
     * @return 管理者サイト権限マスタリスト
     */
    List<RootRoleMt> findAll();
}
