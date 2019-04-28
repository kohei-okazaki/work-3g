package jp.co.ha.business.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.api.service.CommonService;
import jp.co.ha.business.api.service.HealthInfoReferenceService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報照会サービス実装クラス
 *
 */
@Service
public class HealthInfoReferenceServiceImpl extends CommonService implements HealthInfoReferenceService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkRequest(HealthInfoReferenceRequest request) throws BaseException {

		// リクエスト種別チェック
		if (!RequestType.HEALTH_INFO_REFERENCE.is(request.getRequestType())) {
			throw new HealthInfoException(ApiErrorCode.REQUEST_TYPE_INVALID_ERROR,
					"リクエスト種別が一致しません リクエスト種別:" + request.getRequestType().getName());
		}

		// アカウント取得
		Account account = accountSearchService.findByUserId(request.getUserId());
		if (BeanUtil.isNull(account)) {
			throw new HealthInfoException(WebErrorCode.ACCOUNT_ILLEGAL, "アカウントが存在しません userId:" + request.getUserId());
		}

		// API利用判定
		checkApiUse(account, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(HealthInfoReferenceRequest request, HealthInfoReferenceResponse response) throws BaseException {

		List<HealthInfo> healthInfoList = healthInfoSearchService.findByHealthInfoIdAndUserId(request.getHealthInfoId(),
				request.getUserId());
		if (CollectionUtil.isEmpty(healthInfoList)) {
			throw new HealthInfoException(CommonErrorCode.DB_NO_DATA,
					"該当のレコードがみつかりません 健康情報ID:" + request.getHealthInfoId() + ",ユーザID:" + request.getUserId());
		} else if (CollectionUtil.isMultiple(healthInfoList)) {
			throw new HealthInfoException(CommonErrorCode.MULTIPLE_DATA,
					"データが複数存在します 健康情報ID:" + request.getHealthInfoId() + ",ユーザID:" + request.getUserId());
		}

		HealthInfo healthInfo = CollectionUtil.getFirst(healthInfoList);
		BeanUtil.copy(healthInfo, response);
		response.setHealthInfoRegDate(
				DateUtil.toString(healthInfo.getHealthInfoRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
	}

}
