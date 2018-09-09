package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.web.form.LoginForm;
import jp.co.ha.web.service.LoginService;

/**
 * ログインサービス実装クラス
 *
 */
@Service
public class LoginServiceImpl implements LoginService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean invalidPassword(LoginForm loginForm) throws BaseException {
		Account account = accountSearchService.findByUserId(loginForm.getUserId());
		return !loginForm.getPassword().equals(account.getPassword());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean noExistAccount(LoginForm loginForm) throws BaseException {
		Account account = accountSearchService.findByUserId(loginForm.getUserId());
		return BeanUtil.isNull(account);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean invalidAccount(LoginForm loginForm) throws BaseException {
		Account account = accountSearchService.findByUserId(loginForm.getUserId());
		return StringUtil.isTrue(account.getDeleteFlag());
	}

}
