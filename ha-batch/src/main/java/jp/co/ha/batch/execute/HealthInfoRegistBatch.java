package jp.co.ha.batch.execute;

import java.util.List;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * 健康情報登録Batch
 *
 */
public class HealthInfoRegistBatch extends BaseBatch {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(HealthInfoRegistBatch.class);

	/** filepath */
	private static final String PATH = "C:\\app\\data\\healthInfoRegist";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BatchResult execute() {

		LOG.info("execute");



		return BatchResult.SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOptions(List<String> optionList) {
		LOG.info("setOptions");
	}

}
