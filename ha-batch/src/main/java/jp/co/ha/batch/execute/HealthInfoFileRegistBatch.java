package jp.co.ha.batch.execute;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Options;

import jp.co.ha.batch.type.BatchResult;
import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.json.reader.JsonReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.BatchBeanLoader;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateError;
import jp.co.ha.common.validator.ValidateErrorResult;

/**
 * 健康情報ファイル登録Batch
 *
 */
public class HealthInfoFileRegistBatch extends BaseBatch {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(HealthInfoFileRegistBatch.class);
	/** 健康情報設定ファイル */
	private HealthInfoProperties prop = BatchBeanLoader.getBean(HealthInfoProperties.class);
	/** 健康情報登録APIサービス */
	private HealthInfoRegistService service = BatchBeanLoader.getBean(HealthInfoRegistService.class);
	/** 妥当性チェック */
	@SuppressWarnings("unchecked")
	private BeanValidator<HealthInfoRegistRequest> validator = BatchBeanLoader.getBean(BeanValidator.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BatchResult execute() throws BaseException {

		LOG.info("execute");
		List<HealthInfoRegistRequest> requestList = new ArrayList<>();
		JsonReader reader = new JsonReader();
		for (File file : FileUtil.getFileList(prop.getHealthInfoRegistBatchFilePath())) {
			HealthInfoRegistRequest request = reader.read(file, HealthInfoRegistRequest.class);
			request.setRequestType(RequestType.HEALTH_INFO_REGIST);
			requestList.add(request);
		}

		for (HealthInfoRegistRequest request : requestList) {

			// 妥当性チェックを行う
			ValidateErrorResult result = validator.validate(request);
			if (result.hasError()) {
				ValidateError error = result.getFirst();
				throw new HealthInfoException(CommonErrorCode.VALIDATE_ERROR,
						error.getMessage() + " " + error.getName() + "=" + error.getValue());
			}
			service.checkRequest(request);

			HealthInfoRegistResponse response = new HealthInfoRegistResponse();

			// リクエストを送信する
			service.execute(request, response);
		}

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
