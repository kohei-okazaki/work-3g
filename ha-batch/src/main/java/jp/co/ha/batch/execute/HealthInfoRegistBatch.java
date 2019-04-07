package jp.co.ha.batch.execute;

import java.util.List;

import org.apache.commons.cli.Options;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.common.exception.BaseException;
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
	public BatchResult execute() throws BaseException {

		LOG.info("execute");

		return BatchResult.SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Options getOptions(List<String> optionList) {
		LOG.info("getOptions");
		Options options = new Options();
		return options;
	}

}
