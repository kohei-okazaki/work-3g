package jp.co.ha.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.update.AccountUpdateService;
import jp.co.ha.business.update.MailInfoUpdateService;
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

	/** アカウント更新サービス */
	@Autowired
	private AccountUpdateService accountUpdateService;
	/** メール情報更新サービス */
	@Autowired
	private MailInfoUpdateService mailInfoUpdateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Account account, MailInfo mailInfo) {

		// アカウント情報を更新する
		accountUpdateService.update(account);

		// メール情報を更新する
		mailInfoUpdateService.update(mailInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteAccount(AccountSettingForm form) {
		// FIXME AccountDeleteService#delete(String userId)で削除するように修正
//		accountDao.deleteAccount(form.getUserId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mergeAccount(Account account, AccountSettingForm form) {

		account.setPassword(form.getPassword());
		account.setFileEnclosureCharFlag(form.getFileEnclosureCharFlag());
		account.setHealthInfoMaskFlag(form.getHealthInfoMaskFlag());
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
