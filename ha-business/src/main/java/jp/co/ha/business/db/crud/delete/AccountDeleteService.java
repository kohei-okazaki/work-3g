package jp.co.ha.business.db.crud.delete;

/**
 * アカウント削除サービスインターフェース
 *
 * @since 1.0
 */
public interface AccountDeleteService {

    /**
     * 指定されたユーザIDのアカウント情報を削除する
     *
     * @param userId
     *     ユーザID
     */
    void deleteByUserId(String userId);
}
