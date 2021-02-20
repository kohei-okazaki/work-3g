package jp.co.ha.db.mapper.composite;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import jp.co.ha.db.entity.composite.CompositeMonthlyHealthInfo;

/**
 * 月ごとの健康情報Mapper
 *
 * @version 1.0.0
 */
public interface CompositeMonthlyHealthInfoMapper {

    /**
     * 指定した健康情報登録日時の月ごとの健康情報リストを検索する
     *
     * @param from
     *     健康情報登録日時(開始)
     * @param to
     *     健康情報登録日時(終了)
     * @return 月ごとの健康情報リスト
     */
    List<CompositeMonthlyHealthInfo> selectByHealthInfoRegDate(
            @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
