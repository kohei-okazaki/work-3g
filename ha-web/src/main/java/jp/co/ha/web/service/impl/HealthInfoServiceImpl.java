package jp.co.ha.web.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.calc.CalcService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.manager.CodeManager;
import jp.co.ha.common.manager.MainKey;
import jp.co.ha.common.manager.SubKey;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;

/**
 * 健康情報_入力画面サービス実装クラス
 *
 */
@Service
public class HealthInfoServiceImpl implements HealthInfoService {

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 計算サービス */
	@Autowired
	private CalcService calcService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDiffMessage(HealthInfoForm form, HealthInfo lastHealthInfo) {

		CodeManager manager = CodeManager.getInstance();
		SubKey subkey;
		if (form.getWeight().compareTo(lastHealthInfo.getWeight()) == 0) {
			// 変化なしの場合
			subkey = SubKey.EVEN_MESSAGE;
		} else if (form.getWeight().compareTo(lastHealthInfo.getWeight()) == 1) {
			// 増加した場合
			subkey = SubKey.INCREASE_MESSAGE;
		} else {
			// 減少した場合
			subkey = SubKey.DOWN_MESSAGE;
		}
		return manager.getValue(MainKey.HEALTH_INFO_USER_STATUS, subkey);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal getDiffWeight(HealthInfoForm form, HealthInfo lastHealthInfo) {
		return this.calcService.calcDiffWeight(lastHealthInfo.getWeight(), form.getWeight());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isFirstReg(String userId) {

		// ユーザIDから健康情報のリストを取得
		List<HealthInfo> healthInfoList = this.healthInfoSearchService.findByUserId(userId);
		return healthInfoList.isEmpty();
	}

}
