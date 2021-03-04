package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.RootUserRoleMngMt;

/**
 * 管理者サイトユーザ権限管理マスタ作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserRoleMngMtCreateService {

    /**
     * 管理者サイトユーザ権限管理マスタを登録
     *
     * @param entity
     *     管理者サイトユーザ権限管理マスタ
     */
    void create(RootUserRoleMngMt entity);

}
