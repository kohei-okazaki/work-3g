package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.parameter.ParamConst;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.form.AccountRegistForm;
import jp.co.ha.web.service.AccountRegistService;

/**
 * アカウント作成サービス実装クラス
 *
 */
@Service
public class AccountRegistServiceImpl implements AccountRegistService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account toAccount(AccountRegistForm form) {

		Account account = new Account();
		account.setUserId(form.getUserId());
		account.setPassword(form.getPassword());
		account.setDeleteFlag(ParamConst.FLAG_FALSE.getValue());
		account.setRemarks(form.getRemarks());
		account.setFileEnclosureCharFlag(ParamConst.FLAG_FALSE.getValue());
		account.setPasswordExpire(DateUtil.addMonth(DateUtil.getSysDate(), 6));

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean invalidUserId(AccountRegistForm form) {

		// 指定したアカウント情報を検索
		Account account = accountSearchService.findByUserId(form.getUserId());

		// ユーザIDが存在する場合true, 存在しない場合false
		return !StringUtil.isEmpty(account.getUserId());
	}

}
