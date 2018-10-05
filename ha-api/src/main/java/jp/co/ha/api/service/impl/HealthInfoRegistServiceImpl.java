package jp.co.ha.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.service.CommonService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.type.HealthStatus;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;

/**
 * 健康情報登録サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoRegistServiceImpl extends CommonService implements HealthInfoRegistService {

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
	public void checkRequest(HealthInfoRegistRequest request) throws BaseException {

		if (BeanUtil.isNull(request.getRequestType())
				|| StringUtil.isEmpty(request.getUserId())
				|| BeanUtil.isNull(request.getHeight())
				|| BeanUtil.isNull(request.getWeight())) {
			throw new HealthInfoException(ErrorCode.REQUIRE, "必須エラー");
		}

		// リクエスト種別チェック
		if (RequestType.HEALTH_INFO_REGIST != request.getRequestType()) {
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
	public HealthInfoRegistResponse execute(HealthInfoRegistRequest request) throws BaseException {

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

		String userStatus = BeanUtil.isNull(lastHealthInfo)
				? HealthStatus.EVEN.getCode()
				: healthInfoCalcService.getHealthStatus(weight, lastHealthInfo.getWeight()).getCode();
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
	public HealthInfoRegistResponse toResponse(HealthInfo healthInfo) throws BaseException {

		HealthInfoRegistResponse response = new HealthInfoRegistResponse();
		BeanUtil.copy(healthInfo, response);
		response.setRegDate(DateUtil.toString(healthInfo.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));

		HealthInfo lastEntity = healthInfoSearchService.findLastByUserId(healthInfo.getUserId());
		response.setHealthInfoId(lastEntity.getHealthInfoId());
		return response;
	}

}
