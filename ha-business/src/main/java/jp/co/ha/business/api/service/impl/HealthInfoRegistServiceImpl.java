package jp.co.ha.business.api.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ha.business.api.request.HealthInfoRegistRequest;
import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.api.service.CommonService;
import jp.co.ha.business.api.service.HealthInfoRegistService;
import jp.co.ha.business.api.type.RequestType;
import jp.co.ha.business.db.crud.create.HealthInfoCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.exception.ApiErrorCode;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.business.healthInfo.HealthInfoCalcService;
import jp.co.ha.business.healthInfo.type.HealthInfoStatus;
import jp.co.ha.common.api.type.ResultType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.function.ThrowableFunction;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfo;

/**
 * 健康情報登録サービス実装クラス
 *
 */
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
			throw new HealthInfoException(ApiErrorCode.HEALTH_INFO_REG_EMPTY, "必須エラー");
		}

		// リクエスト種別チェック
		if (!RequestType.HEALTH_INFO_REGIST.is(request.getRequestType())) {
			throw new HealthInfoException(ApiErrorCode.REQUEST_TYPE_INVALID_ERROR, "リクエスト種別が一致しません リクエスト種別:" + request.getRequestType().getName());
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
	public HealthInfoRegistResponse execute(HealthInfoRegistRequest request) throws BaseException {

		// リクエストをEntityにつめる
		HealthInfo entity = toEntity().apply(request);

		// Entityの登録処理を行う
		healthInfoCreateService.create(entity);

		return toResponse().apply(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ThrowableFunction<HealthInfoRegistRequest, HealthInfo> toEntity() throws BaseException {

		ThrowableFunction<HealthInfoRegistRequest, HealthInfo> function = request -> {
			String userId = request.getUserId();
			BigDecimal height = request.getHeight();
			BigDecimal weight = request.getWeight();

			// メートルに変換する
			BigDecimal centiMeterHeight = healthInfoCalcService.convertMeterFromCentiMeter().apply(height);

			BigDecimal bmi = healthInfoCalcService.calcBmi(centiMeterHeight, weight, 2);
			BigDecimal standardWeight = healthInfoCalcService.calcStandardWeight(centiMeterHeight, 2);

			// 最後に登録した健康情報を取得する
			HealthInfo lastHealthInfo = healthInfoSearchService.findLastByUserId(userId);

			String status = BeanUtil.isNull(lastHealthInfo)
					? HealthInfoStatus.EVEN.getValue()
					: healthInfoCalcService.getHealthInfoStatus().apply(weight, lastHealthInfo.getWeight()).getValue();

			HealthInfo entity = new HealthInfo();
			BeanUtil.copy(request, entity);
			entity.setBmi(bmi);
			entity.setStandardWeight(standardWeight);
			entity.setHealthInfoStatus(status);

			return entity;
		};

		return function;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ThrowableFunction<HealthInfo, HealthInfoRegistResponse> toResponse() throws BaseException {

		return e -> {
			HealthInfoRegistResponse response = new HealthInfoRegistResponse();
			BeanUtil.copy(e, response);
			response.setRegDate(DateUtil.toString(e.getRegDate(), DateFormatType.YYYYMMDD_HHMMSS));
			HealthInfo lastEntity = healthInfoSearchService.findLastByUserId(e.getUserId());
			response.setHealthInfoId(lastEntity.getHealthInfoId());
			response.setResult(ResultType.SUCCESS);
			return response;
		};
	}

}
