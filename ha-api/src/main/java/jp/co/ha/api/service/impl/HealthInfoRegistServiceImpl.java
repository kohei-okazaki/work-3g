package jp.co.ha.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

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
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.util.DateFormatDefine;
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

		if (Objects.isNull(request.getRequestType())
				|| StringUtil.isEmpty(request.getUserId())
				|| Objects.isNull(request.getHeight())
				|| Objects.isNull(request.getWeight())) {
			throw new HealthInfoException(ErrorCode.REQUIRE, "必須エラー");
		}
		// アカウント取得
		Account account = accountSearchService.findByUserId(request.getUserId());
		if (Objects.isNull(account.getUserId())) {
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
		BigDecimal centiMeterHeight = healthInfoCalcService.convertMeterFromCentiMeter(request.getHeight());

		BigDecimal bmi = healthInfoCalcService.calcBmi(centiMeterHeight, request.getWeight(), 2);
		BigDecimal standardWeight = healthInfoCalcService.calcStandardWeight(centiMeterHeight, 2);

		// 最後に登録した健康情報を取得する
		HealthInfo lastHealthInfo = healthInfoSearchService.findLastByUserId(request.getUserId());
		String userStatus = Objects.nonNull(lastHealthInfo)
						? healthInfoCalcService.getUserStatus(request.getWeight(), lastHealthInfo.getWeight())
						: ParamConst.HEALTH_INFO_USER_STATUS_EVEN.getValue();
		Date regDate = DateUtil.getSysDate();


		HealthInfo entity = new HealthInfo();
		entity.setDataId(getNextDataId(lastHealthInfo));
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
		response.setDataId(healthInfo.getDataId());
		response.setUserId(healthInfo.getUserId());
		response.setHeight(healthInfo.getHeight());
		response.setWeight(healthInfo.getWeight());
		response.setBmi(healthInfo.getBmi());
		response.setStandardWeight(healthInfo.getStandardWeight());
		response.setUserStatus(healthInfo.getUserStatus());
		response.setRegDate(DateUtil.toString(healthInfo.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));

		return response;
	}

	/**
	 * 次のデータIDを取得する
	 * @param dto
	 * @return
	 */
	private String getNextDataId(HealthInfo healthInfo) {
		return Objects.isNull(healthInfo) ? "1" : String.valueOf(Integer.valueOf(healthInfo.getDataId()) + 1);
	}

}
