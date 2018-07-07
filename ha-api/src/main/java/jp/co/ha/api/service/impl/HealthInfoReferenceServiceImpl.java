package jp.co.ha.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoReferenceRequest;
import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.api.service.HealthInfoReferenceService;
import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateFormatPattern;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * 健康情報照会サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoReferenceServiceImpl implements HealthInfoReferenceService {

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
	public void checkRequest(HealthInfoReferenceRequest request) throws HealthInfoException {

		if (StringUtil.isEmpty(request.getRequestId())
				|| StringUtil.isEmpty(request.getUserId())
				|| BeanUtil.isNull(request.getHealthInfoId())) {
			throw new HealthInfoException(ErrorCode.REQUIRE, "必須エラー");
		}

		// リクエスト種別チェック
		if (RequestType.HEALTH_INFO_REFERENCE != RequestType.of(request.getRequestId())) {
			throw new HealthInfoException(ErrorCode.REQUEST_ID_INVALID_ERROR, "リクエスト種別が一致しません requestId:" + request.getRequestId());
		}

		// アカウント取得
		Account account = accountSearchService.findByUserId(request.getUserId());
		if (BeanUtil.isNull(account)) {
			throw new HealthInfoException(ErrorCode.ACCOUNT_ILLEGAL, "アカウントが存在しません userId:" + request.getUserId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse execute(HealthInfoReferenceRequest request) throws HealthInfoException {

		// 指定されたデータIDから健康情報を取得
		HealthInfo entity = healthInfoSearchService.findByHealthInfoId(request.getHealthInfoId());
		if (BeanUtil.isNull(entity)) {
			throw new HealthInfoException(ErrorCode.DB_NO_DATA, "該当のレコードがみつかりません healthInfoId:" + request.getHealthInfoId());
		}

		// レスポンスに変換する
		HealthInfoReferenceResponse apiResponse = toResponse(entity);

		return apiResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse toResponse(HealthInfo healthInfo) {
		// 健康情報照会レスポンスクラス
		HealthInfoReferenceResponse apiResponse = new HealthInfoReferenceResponse();
		BeanUtil.copy(healthInfo, apiResponse);
		apiResponse.setRegDate(DateUtil.toString(healthInfo.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));
		return apiResponse;
	}

}
