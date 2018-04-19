package jp.co.ha.business.healthInfo.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import jp.co.ha.business.healthInfo.HealthInfoService;
import jp.co.ha.common.manager.CodeManager;
import jp.co.ha.common.manager.MainKey;
import jp.co.ha.common.manager.SubKey;

/**
 * 計算サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoServiceImpl implements HealthInfoService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUserStatus(BigDecimal inputWeight, BigDecimal beforeWeight) {

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

}
