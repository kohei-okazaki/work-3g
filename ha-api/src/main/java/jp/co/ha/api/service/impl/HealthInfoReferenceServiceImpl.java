package jp.co.ha.api.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoReferenceRequest;
import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.api.service.HealthInfoReferenceService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報照会サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoReferenceServiceImpl implements HealthInfoReferenceService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkRequest(HealthInfoReferenceRequest request) throws HealthInfoException {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoReferenceResponse execute(HealthInfoReferenceRequest request) throws HealthInfoException {

		// 指定されたデータIDから健康情報を取得
		HealthInfo entity = healthInfoSearchService.findByDataId(request.getDataId());

		// 健康情報レスポンスクラス
		HealthInfoReferenceResponse apiResponse = new HealthInfoReferenceResponse();
		BeanUtils.copyProperties(entity, apiResponse);
		apiResponse.setRegDate(DateUtil.toString(entity.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));

		return apiResponse;
	}

}
