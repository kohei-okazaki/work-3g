package jp.co.ha.business.healthInfo.impl;

import org.springframework.stereotype.Service;

import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfoFileSetting;
import jp.co.ha.common.util.StringUtil;

/**
 * 健康情報利用機能サービス実装クラス<br>
 *
 */
@Service
public class HealthInfoFunctionServiceImpl implements HealthInfoFunctionService {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean useHealthInfoMask(HealthInfoFileSetting entity) {
		return StringUtil.isTrue(entity.getMaskFlag());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean useFileEnclosureCharFlag(HealthInfoFileSetting entity) {
		return StringUtil.isTrue(entity.getEnclosureCharFlag());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean useApi(Account account, String apiKey) {
		String userApiKey = account.getApiKey();
		return apiKey.equals(userApiKey);
	}

}
