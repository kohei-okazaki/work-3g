package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.common.dao.AccountDao;
import jp.co.ha.common.dao.MailInfoDao;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.MailInfo;
import jp.co.ha.web.form.AccountSettingForm;
import jp.co.ha.web.service.AccountSettingService;

/**
 * アカウント設定サービス実装クラス<br>
 *
 */
@Service
public class AccountSettingServiceImpl implements AccountSettingService {

	/** アカウント情報Dao */
	@Autowired
	private AccountDao accountDao;
	/** メール情報Dao */
	@Autowired
	private MailInfoDao mailInfoDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account account, MailInfo mailInfo) {

		// アカウント情報を更新する
		updateAccount(account);

		// メール情報を更新する
		updateMailInfo(mailInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateAccount(Account account) {
		accountDao.updateAccount(account);
	}

	/**
	 * {@inheritDoc}
	 */
	private void updateMailInfo(MailInfo mailInfo) {
		mailInfoDao.updateMailInfo(mailInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAccount(AccountSettingForm form) {
		accountDao.deleteAccount(form.getUserId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mergeAccount(Account account, AccountSettingForm form) {

		account.setPassword(form.getPassword());
		account.setFileEnclosureCharFlag(form.getFileEnclosureCharFlag());
		account.setDeleteFlag(form.getDeleteFlag());
		account.setRemarks(form.getRemarks());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MailInfo convertMailInfo(AccountSettingForm form) {

		MailInfo mailInfo = new MailInfo();
		mailInfo.setUserId(form.getUserId());
		mailInfo.setMailAddress(form.getMailAddress());
		mailInfo.setMailPassword(form.getMailPassword());

		return mailInfo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mergeMailInfo(MailInfo mailInfo, AccountSettingForm form) {

		mailInfo.setMailAddress(form.getMailAddress());
		mailInfo.setMailPassword(form.getMailPassword());
	}

}
