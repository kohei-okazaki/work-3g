package jp.co.ha.business.db.crud.delete;

/**
 * 管理者サイトユーザ権限詳細マスタ削除サービスインターフェース
 *
 * @version 1.0.0
 */
public interface RootUserRoleDetailMtDeleteService {

    /**
     * 指定した管理者サイトユーザ権限管理マスタIDに紐づく管理者サイトユーザ権限詳細マスタを削除する
     *
     * @param seqRootUserRoleMngMtId
     *     管理者サイトユーザ権限管理マスタID
     */
    void deleteByUser(Integer seqRootUserRoleMngMtId);

}
