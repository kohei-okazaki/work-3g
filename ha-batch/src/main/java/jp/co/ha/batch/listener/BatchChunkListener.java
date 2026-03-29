package jp.co.ha.batch.listener;

import org.springframework.batch.core.listener.ChunkListener;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.stereotype.Component;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * Chunkのリスナークラス
 *
 * @version 1.0.0
 */
@Component
public class BatchChunkListener implements ChunkListener<Object, Object> {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BatchChunkListener.class);

    @Override
    public void beforeChunk(Chunk<Object> chunk) {
        LOG.info("before chunk. [context:{" + chunk + "}]");
    }

    @Override
    public void afterChunk(Chunk<Object> chunk) {
        LOG.info("after chunk. [context:{" + chunk + "}]");
    }

    @Override
    public void onChunkError(Exception exception, Chunk<Object> chunk) {
        LOG.error("Exception occurred while chunk. [chunk:{" + chunk + "}]", exception);
    }
    // @Override
    // public void afterChunkError(ChunkContext context) {
    // LOG.error("Exception occurred while chunk. [context:{" +
    // context.toString()
    // + "}, key="
    // + context.getAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY).toString()
    // + "]");
    // }

}
