package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.RootUserNoteInfo;

/**
 * 管理者サイトユーザメモ情報更新サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserNoteInfoUpdateService {

    /**
     * 管理者サイトユーザメモ情報を更新する<br>
     * null項目は更新しない
     * 
     * @param entity
     *     管理者サイトユーザメモ情報
     */
    void updateById(RootUserNoteInfo entity);
}
