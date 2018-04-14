package jp.co.ha.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.common.dao.HealthInfoDao;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.manager.CodeManager;
import jp.co.ha.common.manager.MainKey;
import jp.co.ha.common.manager.SubKey;
import jp.co.ha.common.service.AccountSearchService;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.HealthInfoUtil;

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

		HealthInfo entity = new HealthInfo();
		entity.setUserId(request.getUserId());
		entity.setHeight(request.getHeight());
		entity.setWeight(request.getWeight());
		entity.setRegDate(new Date());

		// メートルに変換する
		BigDecimal centiMeterHeight = HealthInfoUtil.convertMeterFromCentiMeter(request.getHeight());
		BigDecimal bmi = HealthInfoUtil.calcBmi(centiMeterHeight, request.getWeight(), 2);
		entity.setBmi(bmi);
		BigDecimal standardWeight = HealthInfoUtil.calcStandardWeight(centiMeterHeight, 2);
		entity.setStandardWeight(standardWeight);

		// 最後に登録した健康情報を取得する
		HealthInfo lastHealthInfo = healthInfoDao.getLastHealthInfoByUserId(request.getUserId());
		String userStatus = CodeManager.getInstance().getValue(MainKey.HEALTH_INFO_USER_STATUS, SubKey.EVEN);
		if (Objects.nonNull(lastHealthInfo)) {
			// 初回登録でない場合
			userStatus = getUserStatus(request.getWeight(), lastHealthInfo.getWeight());
		}
		entity.setDataId(getNextDataId(lastHealthInfo));
		entity.setUserStatus(userStatus);

		return entity;
	}

	/**
	 * 入力した健康情報.体重と前回入力した健康情報.体重を比較してユーザステータスを返す<br>
	 * @param inputWeight
	 * @param beforeWeight
	 * @return
	 */
	private String getUserStatus(BigDecimal inputWeight, BigDecimal beforeWeight) {

		SubKey subkey;
		if (beforeWeight.compareTo(inputWeight) == 0) {
			subkey = SubKey.EVEN;
		} else if (beforeWeight.compareTo(inputWeight) == -1) {
			subkey = SubKey.INCREASE;
		} else {
			subkey = SubKey.DOWN;
		}

		return CodeManager.getInstance().getValue(MainKey.HEALTH_INFO_USER_STATUS, subkey);
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
