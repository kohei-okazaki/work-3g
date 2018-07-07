package jp.co.ha.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.create.HealthInfoCreateService;
import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.parameter.ParamConst;
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
 * 健康情報登録サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoRegistServiceImpl implements HealthInfoRegistService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
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
	public void checkRequest(HealthInfoRegistRequest request) throws HealthInfoException {

		if (StringUtil.isEmpty(request.getRequestId())
				|| StringUtil.isEmpty(request.getUserId())
				|| BeanUtil.isNull(request.getHeight())
				|| BeanUtil.isNull(request.getWeight())) {
			throw new HealthInfoException(ErrorCode.REQUIRE, "必須エラー");
		}

		// リクエスト種別チェック
		if (RequestType.HEALTH_INFO_REGIST != RequestType.of(request.getRequestId())) {
			throw new HealthInfoException(ErrorCode.REQUEST_ID_INVALID_ERROR, "リクエスト種別が一致しません requestId:" + request.getRequestId());
		}

		// アカウント取得
		Account account = accountSearchService.findByUserId(request.getUserId());
		if (BeanUtil.isNull(account)) {
			throw new HealthInfoException(ErrorCode.ACCOUNT_ILLEGAL, "アカウントが存在しません");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse execute(HealthInfoRegistRequest request) throws HealthInfoException {

		// リクエストをEntityにつめる
		HealthInfo healthInfo = toEntity(request);

		// Entityの登録処理を行う
		healthInfoCreateService.create(healthInfo);

		// レスポンスに変換する
		HealthInfoRegistResponse response = toResponse(healthInfo);

		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo toEntity(HealthInfoRegistRequest request) {

		String userId = request.getUserId();
		BigDecimal height = request.getHeight();
		BigDecimal weight = request.getWeight();

		// メートルに変換する
		BigDecimal centiMeterHeight = healthInfoCalcService.convertMeterFromCentiMeter(height);

		BigDecimal bmi = healthInfoCalcService.calcBmi(centiMeterHeight, weight, 2);
		BigDecimal standardWeight = healthInfoCalcService.calcStandardWeight(centiMeterHeight, 2);

		// 最後に登録した健康情報を取得する
		HealthInfo lastHealthInfo = healthInfoSearchService.findLastByUserId(userId);

		String userStatus = BeanUtil.isNull(lastHealthInfo)
				? ParamConst.HEALTH_INFO_USER_STATUS_EVEN.getValue()
				: healthInfoCalcService.getUserStatus(weight, lastHealthInfo.getWeight());
		Date regDate = DateUtil.getSysDate();

		HealthInfo entity = new HealthInfo();
		entity.setUserId(userId);
		entity.setHeight(height);
		entity.setWeight(weight);
		entity.setBmi(bmi);
		entity.setStandardWeight(standardWeight);
		entity.setUserStatus(userStatus);
		entity.setRegDate(regDate);

		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoRegistResponse toResponse(HealthInfo healthInfo) {

		HealthInfoRegistResponse response = new HealthInfoRegistResponse();
		BeanUtil.copy(healthInfo, response);
		response.setRegDate(DateUtil.toString(healthInfo.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));

		HealthInfo lastEntity = healthInfoSearchService.findLastByUserId(healthInfo.getUserId());
		response.setHealthInfoId(lastEntity.getHealthInfoId());
		return response;
	}

}
