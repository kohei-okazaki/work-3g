package jp.co.ha.business.healthInfo.impl;

import org.springframework.stereotype.Service;

import jp.co.ha.business.healthInfo.HealthInfoFunctionService;
import jp.co.ha.common.entity.Account;
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
	public boolean useHealthInfoMask(Account account) {
		return StringUtil.isTrue(account.getHealthInfoMaskFlag());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean useFileEnclosureCharFlag(Account account) {
		return StringUtil.isTrue(account.getFileEnclosureCharFlag());
	}

}
