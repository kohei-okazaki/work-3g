package jp.co.ha.dashboard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.create.MailInfoCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.business.db.crud.update.MailInfoUpdateService;
import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
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
	public void execute(AccountDto dto) throws BaseException {

		// アカウント情報を検索し、アカウント情報をDTOにマージする
		Account befAccount = accountSearchService.findByUserId(dto.getUserId());
		mergeAccount(dto, befAccount);

		// メール情報を検索し、メール情報にフォームをマージする
		MailInfo befMailInfo = mailInfoSearchService.findByUserId(dto.getUserId());
		if (BeanUtil.notNull(befMailInfo)) {
			mergeMailInfo(dto, befMailInfo);
		}

		if (BeanUtil.isNull(befMailInfo)) {
			// メール情報が登録されてない場合
			befMailInfo = new MailInfo();
			mergeMailInfo(dto, befMailInfo);
			// メール情報を新規登録する
			mailInfoCreateService.create(befMailInfo);
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
	 * アカウントDTOをアカウント情報にマージする
	 *
	 * @param dto
	 *     アカウントDTO
	 * @param account
	 *     アカウント情報
	 */
	private void mergeAccount(AccountDto dto, Account account) {

		BeanUtil.copy(dto, account, List.of("userId"), (src, dest) -> {
			AccountDto srcDto = (AccountDto) src;
			Account destEntity = (Account) dest;
			destEntity.setPasswordExpire(DateUtil.toDate(srcDto.getPasswordExpire(), DateFormatType.YYYYMMDD));
		});

	}

	/**
	 * アカウントDTOをメール情報にマージする
	 *
	 * @param dto
	 *     アカウントDTO
	 * @param mailInfo
	 *     メール情報
	 */
	private void mergeMailInfo(AccountDto dto, MailInfo mailInfo) {

		BeanUtil.copy(dto, mailInfo, List.of("userId"));

	}

}
