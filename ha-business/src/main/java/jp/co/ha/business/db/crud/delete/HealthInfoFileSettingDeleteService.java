package jp.co.ha.business.db.crud.delete;

/**
 * 健康情報ファイル設定削除サービスインターフェース
 *
 * @since 1.0
 */
public interface HealthInfoFileSettingDeleteService {

    /**
     * 指定したユーザIDの健康情報ファイル設定を削除する
     *
     * @param userId
     *     ユーザID
     */
    void deleteByUserId(String userId);
}
