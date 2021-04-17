package jp.co.ha.business.db.crud.delete;

/**
 * 管理者サイトユーザメモ情報削除サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserNoteInfoDeleteService {

    /**
     * 指定した管理者サイトユーザメモ情報IDのレコードを削除する
     *
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     */
    void delete(Long seqRootUserNoteInfoId);

}
