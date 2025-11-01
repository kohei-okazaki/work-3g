package jp.co.ha.db.mapper.composite;

import java.time.LocalDateTime;
import java.util.List;

import jp.co.ha.db.entity.HealthInfo;

/**
 * 連携用健康情報Mapper
 * 
 * @version 1.0.0
 */
public interface MigrateHealthInfoMapper {

    /**
     * 連携用健康情報リストを検索する
     * 
     * @param from
     *     健康情報登録日時(開始)
     * @param to
     *     健康情報登録日時(終了)
     * @return 連携用健康情報リスト
     */
    List<HealthInfo> selectByHealthInfoRegDate(LocalDateTime from, LocalDateTime to);
}
