package jp.co.ha.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.type.RegixType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.db.entity.Account;
import jp.co.ha.web.service.HealthInfoFileRegistService;

/**
 * 健康情報ファイル入力画面サービス<br>
 *
 */
@Service
public class HealthInfoFileRegistServiceImpl implements HealthInfoFileRegistService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** 健康情報登録APIサービス */
	@Autowired
	private HealthInfoRegistService healthInfoRegistService;

	/**
	 * 健康情報CSVアップロードモデルリストから健康情報APIリクエストのリストに変換する<br>
	 *
	 * @param modelList
	 *     健康情報CSVアップロードモデルリスト
	 * @param userId
	 *     ユーザID
	 * @return 健康情報CSVアップロードモデルリスト
	 * @throws BaseException
	 *     基底例外
	 */
	private List<HealthInfoRegistRequest> toRequestList(List<HealthInfoCsvUploadModel> modelList, String userId) throws BaseException {
		Account account = accountSearchService.findByUserId(userId);
		List<HealthInfoRegistRequest> requestList = new ArrayList<>();
		for (HealthInfoCsvUploadModel csvModel : modelList) {
			HealthInfoRegistRequest request = new HealthInfoRegistRequest();
			BeanUtil.copy(csvModel, request);
			request.setRequestType(RequestType.HEALTH_INFO_REGIST);
			request.setHeight(new BigDecimal(csvModel.getHeight()));
			request.setWeight(new BigDecimal(csvModel.getWeight()));
			request.setApiKey(account.getApiKey());
			requestList.add(request);
		}
		return requestList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void formatCheck(List<HealthInfoCsvUploadModel> modelList, String userId) throws BaseException {
		for (int i = 0; i < modelList.size(); i++) {
			HealthInfoCsvUploadModel model = modelList.get(i);
			if (!userId.equals(model.getUserId())) {
				throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "レコード：" + ++i + "行目\r\nユーザIDの項目が不正です。ユーザID：" + model.getUserId());
			}
			if (!RegixType.HALF_NUMBER_PERIOD.is(model.getHeight())) {
				throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "レコード：" + ++i + "行目\r\n身長の項目が不正です。身長：" + model.getHeight());
			}
			if (!RegixType.HALF_NUMBER_PERIOD.is(model.getWeight())) {
				throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "レコード：" + ++i + "行目\r\n体重の項目が不正です。体重：" + model.getWeight());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void regist(List<HealthInfoCsvUploadModel> modelList, String userId) throws BaseException {
		for (HealthInfoRegistRequest apiRequest : toRequestList(modelList, userId)) {
			healthInfoRegistService.checkRequest(apiRequest);
			healthInfoRegistService.execute(apiRequest);
		}
	}

}
