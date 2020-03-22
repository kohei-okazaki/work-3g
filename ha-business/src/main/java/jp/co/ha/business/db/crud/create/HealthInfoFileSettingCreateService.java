package jp.co.ha.business.db.crud.create;

import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康情報ファイル設定作成サービスインターフェース
 *
 * @since 1.0
 */
public interface HealthInfoFileSettingCreateService {

    /**
     * 指定した健康情報ファイル設定を登録する
     *
     * @param entity
     *     健康情報ファイル設定
     */
    void create(HealthInfoFileSetting entity);
}
