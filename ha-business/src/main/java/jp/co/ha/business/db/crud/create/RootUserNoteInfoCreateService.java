package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.RootUserNoteInfo;

/**
 * 管理者サイトユーザメモ情報登録サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserNoteInfoCreateService {

    /**
     * 管理者サイトユーザメモ情報を登録する
     *
     * @param entity
     *     管理者サイトユーザメモ情報
     */
    void create(RootUserNoteInfo entity);

}
