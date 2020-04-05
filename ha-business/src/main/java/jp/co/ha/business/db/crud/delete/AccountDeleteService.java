package jp.co.ha.business.db.crud.delete;

/**
 * アカウント削除サービスインターフェース
 *
 * @version 1.0.0
 */
public interface AccountDeleteService {

    /**
     * 指定されたユーザIDのアカウント情報を削除する
     *
     * @param userId
     *     ユーザID
     */
    void deleteById(String userId);
}
