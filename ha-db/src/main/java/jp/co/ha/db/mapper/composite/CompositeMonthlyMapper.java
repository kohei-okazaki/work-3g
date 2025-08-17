package jp.co.ha.db.mapper.composite;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import jp.co.ha.db.entity.composite.CompositeMonthlyRegData;

/**
 * 月ごとの健康情報Mapper
 *
 * @version 1.0.0
 */
public interface CompositeMonthlyMapper {

    /**
     * 指定した健康情報登録日時の健康情報の月ごとの登録情報リストを検索する
     *
     * @param from
     *     健康情報登録日時(開始)
     * @param to
     *     健康情報登録日時(終了)
     * @return 月ごとの登録情報リスト
     */
    List<CompositeMonthlyRegData> selectByHealthInfoRegDate(
            @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    /**
     * 指定した登録日時のユーザ情報の月ごとの登録情報リストを検索する
     *
     * @param from
     *     登録日時(開始)
     * @param to
     *     登録日時(終了)
     * @return 月ごとの登録情報リスト
     */
    List<CompositeMonthlyRegData> selectUserByRegDate(
            @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
