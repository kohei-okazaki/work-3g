package jp.co.ha.business.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.request.HealthInfoReferenceRequest;
import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.api.service.CommonService;
import jp.co.ha.business.api.service.HealthInfoReferenceService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報照会サービス実装クラス
 *
 * @since 1.0
 */
@Service
public class HealthInfoReferenceServiceImpl extends CommonService implements HealthInfoReferenceService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkRequest(HealthInfoReferenceRequest request) throws BaseException {

		// リクエスト種別チェック
		if (RequestType.HEALTH_INFO_REFERENCE != request.getRequestType()) {
			throw new BusinessException(ApiErrorCode.REQUEST_TYPE_INVALID_ERROR,
					"リクエスト種別が一致しません requestType:" + request.getRequestType().getName());
		}

		// API利用判定
		checkApiUse(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(HealthInfoReferenceRequest request, HealthInfoReferenceResponse response) throws BaseException {

		List<HealthInfo> healthInfoList = healthInfoSearchService.findByHealthInfoIdAndUserId(request.getHealthInfoId(),
				request.getUserId());
		if (CollectionUtil.isEmpty(healthInfoList)) {
			throw new BusinessException(CommonErrorCode.DB_NO_DATA,
					"該当のレコードが見つかりません healthInfoId:" + request.getHealthInfoId() + ", userId:" + request.getUserId());
		} else if (CollectionUtil.isMultiple(healthInfoList)) {
			throw new BusinessException(CommonErrorCode.MULTIPLE_DATA,
					"該当のデータが複数存在します healthInfoId:" + request.getHealthInfoId() + ", userId:" + request.getUserId());
		}

		HealthInfo healthInfo = CollectionUtil.getFirst(healthInfoList);
		BeanUtil.copy(healthInfo, response);

	}

}
