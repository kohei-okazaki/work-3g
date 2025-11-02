package jp.co.ha.batch.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * Chunkのリスナークラス
 *
 * @version 1.0.0
 */
@Component
public class BatchChunkListener implements ChunkListener {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BatchChunkListener.class);

    @Override
    public void beforeChunk(ChunkContext context) {
        LOG.info("before chunk. [context:{" + context + "}]");
    }

    @Override
    public void afterChunk(ChunkContext context) {
        LOG.info("after chunk. [context:{" + context + "}]");
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        LOG.error("Exception occurred while chunk. [context:{" + context.toString()
                + "}, key="
                + context.getAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY).toString()
                + "]");
    }

}
