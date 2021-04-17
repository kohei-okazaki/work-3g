package jp.co.ha.business.db.crud.update;

/**
 * 管理者サイトユーザメモ情報更新サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserNoteInfoUpdateService {

    /**
     * 管理者サイトユーザメモ情報を更新する
     *
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     * @param title
     *     件名
     */
    void update(Long seqRootUserNoteInfoId, String title);
}
