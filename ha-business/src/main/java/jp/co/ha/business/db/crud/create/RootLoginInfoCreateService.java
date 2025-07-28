package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.RootLoginInfo;

/**
 * 管理者サイトユーザログイン情報作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootLoginInfoCreateService {

    /**
     * 指定した管理者サイトユーザログイン情報を登録する
     *
     * @param entity
     *     管理者サイトユーザログイン情報
     */
    void create(RootLoginInfo entity);
}
