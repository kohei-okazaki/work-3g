package jp.co.ha.dashboard.healthinfo.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.HealthInfoRegistApi;
import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.api.type.TestMode;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.validator.BeanValidator;
import jp.co.ha.common.validator.ValidateError;
import jp.co.ha.common.validator.ValidateErrorResult;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoFileRegistService;
import jp.co.ha.db.entity.Account;

/**
 * 健康情報ファイル入力画面サービス実装クラス
 *
 * @since 1.0
 */
@Service
public class HealthInfoFileRegistServiceImpl implements HealthInfoFileRegistService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** 健康情報登録API */
	@Autowired
	private HealthInfoRegistApi registApi;
	/** 妥当性チェック */
	@Autowired
	private BeanValidator<HealthInfoCsvUploadModel> validator;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void formatCheck(List<HealthInfoCsvUploadModel> modelList, String userId) throws BaseException {
		for (int i = 0; i < modelList.size(); i++) {
			HealthInfoCsvUploadModel model = modelList.get(i);
			ValidateErrorResult result = validator.validate(model);

			// 相関チェック
			if (!model.getUserId().equals(userId)) {
				throw new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
						++i + "行目のユーザIDが不正です");
			}
			if (result.hasError()) {
				ValidateError error = result.getFirst();
				throw new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
						++i + "行目のファイルフォーマットが不正です " + error.getMessage());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void regist(List<HealthInfoCsvUploadModel> modelList, String userId) throws BaseException {
		for (HealthInfoRegistRequest apiRequest : toRequestList(modelList, userId)) {
			registApi.execute(apiRequest, new HealthInfoRegistResponse());
		}
	}

	/**
	 * 健康情報CSVアップロードモデルリストから健康情報登録APIリクエストのリストに変換する
	 *
	 * @param modelList
	 *     健康情報CSVアップロードモデルリスト
	 * @param userId
	 *     ユーザID
	 * @return 健康情報登録APIリクエストリスト
	 * @throws BaseException
	 *     基底例外
	 */
	private List<HealthInfoRegistRequest> toRequestList(List<HealthInfoCsvUploadModel> modelList, String userId)
			throws BaseException {
		Account account = accountSearchService.findByUserId(userId).get();
		return modelList.stream().map(e -> {
			HealthInfoRegistRequest request = new HealthInfoRegistRequest();
			BeanUtil.copy(e, request, (src, dest) -> {
				HealthInfoCsvUploadModel srcModel = (HealthInfoCsvUploadModel) src;
				HealthInfoRegistRequest destRequest = (HealthInfoRegistRequest) dest;
				destRequest.setHeight(new BigDecimal(srcModel.getHeight()));
				destRequest.setWeight(new BigDecimal(srcModel.getWeight()));
				destRequest.setRequestType(RequestType.HEALTH_INFO_REGIST);
				destRequest.setTestMode(TestMode.DB_REGIST);
			});
			request.setApiKey(account.getApiKey());

			return request;
		}).collect(Collectors.toList());
	}

}