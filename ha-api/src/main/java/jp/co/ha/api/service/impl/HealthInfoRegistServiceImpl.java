package jp.co.ha.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.db.create.HealthInfoCreateService;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.entity.HealthInfo;
import jp.co.ha.business.db.find.AccountSearchService;
import jp.co.ha.business.db.find.HealthInfoSearchService;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.business.type.HealthStatus;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
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
	/** 健康情報機能サービス */
	@Autowired
	private HealthInfoFunctionService functionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkRequest(HealthInfoRegistRequest request) throws BaseException {

		if (StringUtil.isEmpty(request.getRequestType().getRequestId())
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

		// ユーザIDとAPIキーのチェックを行う
		if (!functionService.useApi(account, request.getApiKey())) {
			throw new HealthInfoException(ErrorCode.API_EXEC_ERROR, "APIの実行に失敗しました ユーザID:" + request.getUserId());
		}
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
				: healthInfoCalcService.getUserStatus(weight, lastHealthInfo.getWeight()).getCode();
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
		response.setRegDate(DateUtil.toString(healthInfo.getRegDate(), DateFormatPattern.YYYYMMDD_HHMMSS));

		HealthInfo lastEntity = healthInfoSearchService.findLastByUserId(healthInfo.getUserId());
		response.setHealthInfoId(lastEntity.getHealthInfoId());
		return response;
	}

}
