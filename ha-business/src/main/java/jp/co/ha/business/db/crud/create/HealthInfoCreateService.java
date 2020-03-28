package jp.co.ha.business.db.crud.create;

import java.util.List;

import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報作成サービスインターフェース
 *
 * @version 1.0.0
 */
public interface HealthInfoCreateService {

    /**
     * 指定した健康情報を登録する
     *
     * @param entity
     *     健康情報
     */
    void create(HealthInfo entity);

    /**
     * 指定した健康情報リストを登録する
     *
     * @param entityList
     *     健康情報リスト
     */
    void create(List<HealthInfo> entityList);
}
