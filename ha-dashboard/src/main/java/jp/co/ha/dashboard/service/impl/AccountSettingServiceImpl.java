package jp.co.ha.dashboard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.create.MailInfoCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.business.db.crud.update.MailInfoUpdateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.dashboard.form.AccountSettingForm;
import jp.co.ha.dashboard.service.AccountSettingService;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.MailInfo;

/**
 * アカウント設定サービス実装クラス
 *
 */
@Service
public class AccountSettingServiceImpl implements AccountSettingService {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** アカウント更新サービス */
	@Autowired
	private AccountUpdateService accountUpdateService;
	/** メール情報検索サービス */
	@Autowired
	private MailInfoSearchService mailInfoSearchService;
	/** メール情報作成サービス */
	@Autowired
	private MailInfoCreateService mailInfoCreateService;
	/** メール情報更新サービス */
	@Autowired
	private MailInfoUpdateService mailInfoUpdateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(AccountSettingForm form) throws BaseException {

		// アカウント情報を検索し、アカウント情報にフォームをマージする
		Account befAccount = accountSearchService.findByUserId(form.getUserId());
		mergeAccount(befAccount, form);

		// メール情報を検索し、メール情報にフォームをマージする
		MailInfo befMailInfo = mailInfoSearchService.findByUserId(form.getUserId());
		if (BeanUtil.notNull(befMailInfo)) {
			mergeMailInfo(befMailInfo, form);
		}

		if (BeanUtil.isNull(befMailInfo)) {
			// メール情報が登録されてない場合
			MailInfo mailInfo = convertMailInfo(form);
			// メール情報を新規登録する
			mailInfoCreateService.create(mailInfo);
			// アカウント情報を更新する
			accountUpdateService.update(befAccount);
		} else {
			// 更新処理を行う
			update(befAccount, befMailInfo);
		}
	}

	/**
	 * 更新処理を行う
	 *
	 * @param account
	 *     アカウント情報
	 * @param mailInfo
	 *     メール情報
	 * @throws BaseException
	 *     基底例外
	 */
	private void update(Account account, MailInfo mailInfo) throws BaseException {

		// アカウント情報を更新する
		accountUpdateService.update(account);

		// メール情報を更新する
		mailInfoUpdateService.update(mailInfo);
	}

	/**
	 * フォーム情報をアカウント情報にマージする
	 *
	 * @param account
	 *     アカウント情報
	 * @param form
	 *     アカウント設定情報フォーム
	 */
	private void mergeAccount(Account account, AccountSettingForm form) {
		BeanUtil.copy(form, account, List.of("userId"));
		account.setPasswordExpire(DateUtil.toDate(form.getPasswordExpire(), DateFormatType.YYYYMMDD));
	}

	/**
	 * フォーム情報をメール情報に変換する
	 *
	 * @param form
	 *     アカウント設定情報フォーム
	 * @return メール情報
	 */
	private MailInfo convertMailInfo(AccountSettingForm form) {
		MailInfo mailInfo = new MailInfo();
		BeanUtil.copy(form, mailInfo);
		return mailInfo;
	}

	/**
	 * フォーム情報をメール情報にマージする
	 *
	 * @param mailInfo
	 *     メール情報
	 * @param form
	 *     アカウント設定情報フォーム
	 */
	private void mergeMailInfo(MailInfo mailInfo, AccountSettingForm form) {
		BeanUtil.copy(form, mailInfo, List.of("userId"));
	}

}