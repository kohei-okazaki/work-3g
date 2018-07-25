package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.parameter.ParamConst;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.AlgorithmException;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.system.PasswordEncoder;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.form.AccountRegistForm;
import jp.co.ha.web.service.AccountRegistService;
import jp.co.ha.web.service.annotation.Sha256;

/**
 * アカウント作成サービス実装クラス
 *
 */
@Service
public class AccountRegistServiceImpl implements AccountRegistService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** パスワード作成サービス */
	@Autowired
	@Sha256
	private PasswordEncoder encoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account toAccount(AccountRegistForm form) throws AlgorithmException {

		Account account = new Account();
		BeanUtil.copy(form, account);
		account.setDeleteFlag(ParamConst.FLAG_FALSE.getValue());
		account.setPasswordExpire(DateUtil.addMonth(DateUtil.getSysDate(), 6));

		String apiKey = encoder.execute(form.getPassword(), form.getUserId());
		account.setApiKey(apiKey);

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean invalidUserId(AccountRegistForm form) throws BaseAppException {

		// 指定したアカウント情報を検索
		Account account = accountSearchService.findByUserId(form.getUserId());

		if (BeanUtil.isNull(account)) {
			// 初回登録時のアカウントの場合、後続のチェックを行わない
			return false;
		}

		// ユーザIDが存在する場合true, 存在しない場合false
		return !StringUtil.isEmpty(account.getUserId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HealthInfoFileSetting toHealthInfoFileSetting(AccountRegistForm form) {
		HealthInfoFileSetting entity = new HealthInfoFileSetting();
		entity.setUserId(form.getUserId());
		entity.setEnclosureCharFlag(StringUtil.FALSE_FLAG);
		entity.setHeaderFlag(StringUtil.FALSE_FLAG);
		entity.setFooterFlag(StringUtil.FALSE_FLAG);
		entity.setMaskFlag(StringUtil.FALSE_FLAG);
		return entity;
	}

}
