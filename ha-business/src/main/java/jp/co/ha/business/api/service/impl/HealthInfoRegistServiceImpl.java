package jp.co.ha.business.api.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.CommonService;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報登録サービス実装クラス
 *
 */
@Service
public class HealthInfoRegistServiceImpl extends CommonService implements HealthInfoRegistService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 健康情報計算サービス */
	@Autowired
	private HealthInfoCalcService healthInfoCalcService;
	/** 健康情報作成サービス */
	@Autowired
	private HealthInfoCreateService healthInfoCreateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkRequest(HealthInfoRegistRequest request) throws BaseException {

		// リクエスト種別チェック
		if (!RequestType.HEALTH_INFO_REGIST.is(request.getRequestType())) {
			throw new BusinessException(ApiErrorCode.REQUEST_TYPE_INVALID_ERROR,
					"リクエスト種別が一致しません リクエスト種別:" + request.getRequestType().getName());
		}

		// API利用判定
		checkApiUse(request);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(HealthInfoRegistRequest request, HealthInfoRegistResponse response) throws BaseException {

		// リクエストをEntityにつめる
		HealthInfo entity = toEntity(request);

		// Entityの登録処理を行う
		healthInfoCreateService.create(entity);

		BeanUtil.copy(entity, response);
		HealthInfo lastEntity = healthInfoSearchService.findLastByUserId(entity.getUserId());
		response.setHealthInfoId(lastEntity.getHealthInfoId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo toEntity(HealthInfoRegistRequest request) throws BaseException {

		String userId = request.getUserId();
		BigDecimal height = request.getHeight();
		BigDecimal weight = request.getWeight();

		// メートルに変換する
		BigDecimal centiMeterHeight = healthInfoCalcService.convertMeterFromCentiMeter(height);

		BigDecimal bmi = healthInfoCalcService.calcBmi(centiMeterHeight, weight, 2);
		BigDecimal standardWeight = healthInfoCalcService.calcStandardWeight(centiMeterHeight, 2);

		// 最後に登録した健康情報を取得する
		HealthInfo lastHealthInfo = healthInfoSearchService.findLastByUserId(userId);

		HealthInfoStatus status = BeanUtil.isNull(lastHealthInfo)
				? HealthInfoStatus.EVEN
				: healthInfoCalcService.getHealthInfoStatus(weight, lastHealthInfo.getWeight());

		HealthInfo entity = new HealthInfo();
		BeanUtil.copy(request, entity);
		entity.setBmi(bmi);
		entity.setStandardWeight(standardWeight);
		entity.setHealthInfoStatus(status.getValue());
		entity.setHealthInfoRegDate(DateUtil.getSysDate());

		return entity;

	}

}
