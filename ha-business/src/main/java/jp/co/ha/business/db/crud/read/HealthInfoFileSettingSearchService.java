package jp.co.ha.business.db.crud.read;

import java.util.Optional;

import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報ファイル設定検索サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoFileSettingSearchService {

    /**
     * 健康情報ファイル設定を検索する
     *
     * @param userId
     *     ユーザID
     * @return 健康情報ファイル設定
     */
    Optional<HealthInfoFileSetting> findByUserId(String userId);

}
