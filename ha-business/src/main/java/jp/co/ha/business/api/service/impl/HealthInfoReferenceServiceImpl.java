package jp.co.ha.business.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.api.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.api.service.CommonService;
import jp.co.ha.business.api.service.HealthInfoReferenceService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.common.api.type.ResultType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報照会サービス実装クラス
 *
 */
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

		if (BeanUtil.isNull(request.getRequestType())
				|| StringUtil.isEmpty(request.getUserId())
				|| BeanUtil.isNull(request.getHealthInfoId())) {
			throw new HealthInfoException(ErrorCode.HEALTH_INFO_REG_EMPTY, "必須エラー");
		}

		// リクエスト種別チェック
		if (!RequestType.HEALTH_INFO_REFERENCE.is(request.getRequestType())) {
			throw new HealthInfoException(ErrorCode.REQUEST_ID_INVALID_ERROR, "リクエスト種別が一致しません リクエスト種別:" + request.getRequestType().getName());
		}

		// アカウント取得
		Account account = accountSearchService.findByUserId(request.getUserId());
		if (BeanUtil.isNull(account)) {
			throw new HealthInfoException(ErrorCode.ACCOUNT_ILLEGAL, "アカウントが存在しません userId:" + request.getUserId());
		}

		// API利用判定
		useApi(account, request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse execute(HealthInfoReferenceRequest request) throws BaseException {

		HealthInfo entity = healthInfoSearchService.findByHealthInfoId(request.getHealthInfoId());
		if (BeanUtil.isNull(entity)) {
			throw new HealthInfoException(ErrorCode.DB_NO_DATA, "該当のレコードがみつかりません 健康情報ID:" + request.getHealthInfoId());
		}

		// レスポンスに変換する
		HealthInfoReferenceResponse response = toResponse(entity);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse toResponse(HealthInfo healthInfo) {

		HealthInfoReferenceResponse response = new HealthInfoReferenceResponse();
		BeanUtil.copy(healthInfo, response);
		response.setRegDate(DateUtil.toString(healthInfo.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
		response.setResult(ResultType.SUCCESS);
		return response;
	}

}