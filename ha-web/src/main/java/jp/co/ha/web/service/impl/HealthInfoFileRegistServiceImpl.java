package jp.co.ha.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.RegixPattern;
import jp.co.ha.web.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.web.service.HealthInfoFileRegistService;

/**
 * 健康情報ファイル入力画面サービス<br>
 *
 */
@Service
public class HealthInfoFileRegistServiceImpl implements HealthInfoFileRegistService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HealthInfoRegistRequest> toRequestList(List<HealthInfoCsvUploadModel> modelList) {
		List<HealthInfoRegistRequest> requestList = new ArrayList<HealthInfoRegistRequest>();
		for (HealthInfoCsvUploadModel csvModel : modelList) {
			HealthInfoRegistRequest request = new HealthInfoRegistRequest();
			BeanUtil.copy(csvModel, request);
			request.setRequestType(RequestType.HEALTH_INFO_REGIST);
			request.setHeight(new BigDecimal(csvModel.getHeight()));
			request.setWeight(new BigDecimal(csvModel.getWeight()));
			requestList.add(request);
		}
		return requestList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void formatCheck(List<HealthInfoCsvUploadModel> modelList) throws HealthInfoException {
		for (int i = 0; i < modelList.size(); i++) {
			HealthInfoCsvUploadModel model = modelList.get(i);
			RegixPattern.isPattern(model.getHeight(), RegixPattern.HALF_NUMBER_PERIOD);
			if (!RegixPattern.isPattern(model.getHeight(), RegixPattern.HALF_NUMBER_PERIOD)) {
				throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "レコード：" + ++i + "行目\r\n身長の項目が不正です。身長：" + model.getHeight());
			}
			if (!RegixPattern.isPattern(model.getWeight(), RegixPattern.HALF_NUMBER_PERIOD)) {
				throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "レコード：" + ++i + "行目\r\n体重の項目が不正です。体重：" + model.getWeight());
			}
		}
	}

}
