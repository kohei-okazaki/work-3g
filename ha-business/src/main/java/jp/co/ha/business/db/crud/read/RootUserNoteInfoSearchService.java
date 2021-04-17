package jp.co.ha.business.db.crud.read;

import java.util.List;

import jp.co.ha.db.entity.RootUserNoteInfo;

/**
 * 管理者サイトユーザメモ情報検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserNoteInfoSearchService {

    /**
     * 指定したログインIDの管理者サイトユーザメモ情報を全件返す
     *
     * @param seqLoginId
     *     ログインID
     * @return 管理者サイトユーザメモ情報リスト
     */
    List<RootUserNoteInfo> findBySeqLoginId(Long seqLoginId);

    /**
     * 指定した管理者サイトユーザメモ情報IDの管理者サイトユーザメモ情報を返す
     *
     * @param seqRootUserNoteInfoId
     *     管理者サイトユーザメモ情報ID
     * @return 管理者サイトユーザメモ情報
     */
    RootUserNoteInfo findById(Long seqRootUserNoteInfoId);

}
