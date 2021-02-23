package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.RootLoginInfo;

/**
 * 管理者サイトユーザログイン情報更新サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootLoginInfoUpdateService {

    /**
     * 管理者サイトユーザログイン情報を更新する
     *
     * @param entity
     *     管理者サイトユーザログイン情報
     */
    void update(RootLoginInfo entity);

}
