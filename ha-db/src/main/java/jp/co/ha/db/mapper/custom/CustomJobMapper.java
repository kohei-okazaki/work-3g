package jp.co.ha.db.mapper.custom;

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
}
