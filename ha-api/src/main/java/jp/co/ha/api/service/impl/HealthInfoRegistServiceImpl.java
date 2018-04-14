package jp.co.ha.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.calc.CalcService;
import jp.co.ha.business.healthInfo.HealthInfoService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.manager.CodeManager;
import jp.co.ha.common.manager.MainKey;
import jp.co.ha.common.manager.SubKey;
import jp.co.ha.common.service.AccountSearchService;
import jp.co.ha.common.service.HealthInfoSearchService;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;

/**
 * 健康情報登録サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoRegistServiceImpl implements HealthInfoRegistService {

	/** 健康情報Dao */
	@Autowired
	private HealthInfoDao healthInfoDao;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** 計算サービス */
	@Autowired
	private CalcService calcService;
	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 健康情報ビジネスサービス */
	@Autowired
	private HealthInfoService healthInfoService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkRequest(HealthInfoRegistRequest request) throws HealthInfoException {

		Account account = accountSearchService.findAccountByUserId(request.getUserId());
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

		// 登録処理を行う
		healthInfoDao.registHealthInfo(healthInfo);

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
		BigDecimal centiMeterHeight = calcService.convertMeterFromCentiMeter(request.getHeight());

		BigDecimal bmi = calcService.calcBmi(centiMeterHeight, request.getWeight(), 2);
		BigDecimal standardWeight = calcService.calcStandardWeight(centiMeterHeight, 2);

		// 最後に登録した健康情報を取得する
		HealthInfo lastHealthInfo = healthInfoSearchService.findLastHealthInfoByUserId(request.getUserId());
		String userStatus = Objects.nonNull(lastHealthInfo)
						? healthInfoService.getUserStatus(request.getWeight(), lastHealthInfo.getWeight())
						: CodeManager.getInstance().getValue(MainKey.HEALTH_INFO_USER_STATUS, SubKey.EVEN);
		Date regDate = new Date();


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
