package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.RootUserRoleDetailMt;

/**
 * 管理者サイトユーザ権限詳細マスタ作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserRoleDetailMtCreateService {

    /**
     * 管理者サイトユーザ権限詳細マスタを登録
     *
     * @param entity
     *     管理者サイトユーザ権限詳細マスタ
     */
    void create(RootUserRoleDetailMt entity);

}
