package jp.co.ha.business.db.crud.delete;

/**
 * 健康情報ファイル設定削除サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoFileSettingDeleteService {

    /**
     * 指定したユーザIDの健康情報ファイル設定を削除する
     *
     * @param userId
     *     ユーザID
     */
    void deleteById(String userId);
}
