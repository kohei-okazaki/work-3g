package jp.co.ha.business.db.crud.update;

import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報更新サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoUpdateService {

    /**
     * 健康情報を更新する
     *
     * @param entity
     *     健康情報
     */
    void update(HealthInfo entity);
}
