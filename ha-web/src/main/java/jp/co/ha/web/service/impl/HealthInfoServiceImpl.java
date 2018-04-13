package jp.co.ha.web.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;

/**
 * 健康情報_入力画面サービス実装クラス
 *
 */
@Service
public class HealthInfoServiceImpl implements HealthInfoService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDiffMessage(HealthInfoForm form, HealthInfo lastHealthInfo) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getDiffWeight(HealthInfoForm form, HealthInfo lastHealthInfo) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfo convertHealthInfo(HealthInfoForm form, String userId, HealthInfo lastHealthInfo) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}



}
