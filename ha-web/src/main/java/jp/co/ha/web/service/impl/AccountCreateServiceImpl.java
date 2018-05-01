package jp.co.ha.web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.manager.CodeManager;
import jp.co.ha.common.manager.MainKey;
import jp.co.ha.common.manager.SubKey;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.form.AccountCreateForm;
import jp.co.ha.web.service.AccountCreateService;

/**
 * アカウント作成サービス実装クラス
 *
 */
@Service
public class AccountCreateServiceImpl implements AccountCreateService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account toAccount(AccountCreateForm form) {

		Account account = new Account();
		account.setUserId(form.getUserId());
		account.setPassword(form.getPassword());
		account.setDeleteFlag(CodeManager.getInstance().getValue(MainKey.FLAG, SubKey.FALSE));
		account.setRemarks(form.getRemarks());
		account.setFileEnclosureCharFlag(CodeManager.getInstance().getValue(MainKey.FLAG, SubKey.FALSE));
		account.setPasswordExpire(DateUtil.addMonth(new Date(), 6));

		return account;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean invalidUserId(AccountCreateForm form) {

		// 指定したアカウント情報を検索
		Account account = accountSearchService.findByUserId(form.getUserId());

		// ユーザIDが存在する場合true, 存在しない場合false
		return !StringUtil.isEmpty(account.getUserId());
	}

}
