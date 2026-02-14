package jp.co.ha.batch.datapurge;

import java.time.LocalDateTime;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import jp.co.ha.batch.base.BatchProperties;
import jp.co.ha.batch.base.BatchProperties.DataPurge;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.db.mapper.custom.DataPurgeMapper;

/**
 * データパージ処理 - Tasklet
 * <table border="1">
 * <tr>
 * <th>テーブル名</th>
 * <th>削除対象となる更新日時</th>
 * </tr>
 * <tr>
 * <td>USER</td>
 * <td>更新日時 + 10年</td>
 * </tr>
 * </table>
 * 
 * @version 1.0.0
 */
@StepScope
@Component
public class DataPurgeTasklet implements Tasklet {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(DataPurgeTasklet.class);
    /** バッチ設定ファイル */
    private BatchProperties batchProperties;
    /** データパージMapper */
    private DataPurgeMapper dataPurgeMapper;

    /**
     * コンストラクタ
     * 
     * @param batchProperties
     *     バッチ設定ファイル
     * @param dataPurgeMapper
     *     データパージMapper
     */
    public DataPurgeTasklet(BatchProperties batchProperties,
            DataPurgeMapper dataPurgeMapper) {
        this.batchProperties = batchProperties;
        this.dataPurgeMapper = dataPurgeMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        for (DataPurge data : batchProperties.getDataPurgeList()) {
            LOG.debug("table_name=%s, expired=%s".formatted(data.getTableName(),
                    data.getExpired()));

            String tableKey = data.getTableName();
            LocalDateTime threshold = LocalDateTime.now()
                    .minusYears(data.getExpired());

            dataPurgeMapper.deleteExpired(tableKey, threshold);

        }
        return RepeatStatus.FINISHED;
    }
}
