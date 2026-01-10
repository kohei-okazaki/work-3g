package jp.co.ha.db.mapper.custom;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import jp.co.ha.db.entity.custom.CustomJobData;

/**
 * Batch起動履歴テーブル<br>
 * 以下のテーブルを結合して取得
 * <ul>
 * <li>BATCH_JOB_INSTANCE</li>
 * <li>BATCH_JOB_EXECUTION</li>
 * <li>BATCH_JOB_EXECUTION_PARAMS</li>
 * </ul>
 * 
 * @version 1.0.0
 */
public interface CustomJobMapper {

    /**
     * Batch起動履歴リストを検索する
     * 
     * @param rowBounds
     *     {@linkplain RowBounds}
     * @return Batch起動履歴リスト
     */
    List<CustomJobData> selectPageable(RowBounds rowBounds);

    /**
     * 件数を取得
     * 
     * @return 件数
     */
    long count();

    /**
     * ページングされたBatch起動履歴リストを検索する
     * 
     * @param from
     *     登録日時(開始)
     * @param to
     *     登録日時(終了)
     * @return Batch起動履歴リスト
     */
    List<CustomJobData> selectByCreateTime(LocalDateTime from, LocalDateTime to);
}
