package jp.co.ha.dashboard.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.create.MailInfoCreateService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.business.db.crud.update.HealthInfoFileSettingUpdateService;
import jp.co.ha.business.db.crud.update.MailInfoUpdateService;
import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.dashboard.service.AccountSettingService;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.db.entity.MailInfo;

/**
 * アカウント設定サービス実装クラス
 *
 * @since 1.0
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
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;
	/** 健康情報ファイル設定更新サービス */
	@Autowired
	private HealthInfoFileSettingUpdateService healthInfoFileSettingUpdateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(AccountDto dto) throws BaseException {

		// アカウント情報を検索し、Dtoの内容をマージする
		Account befAccount = accountSearchService.findByUserId(dto.getUserId()).get();
		mergeAccount(dto, befAccount);
		// アカウント情報を更新する
		accountUpdateService.update(befAccount);

		// メール情報を検索し、Dtoの内容をマージする
		Optional<MailInfo> befMailInfo = mailInfoSearchService.findByUserId(dto.getUserId());
		if (befMailInfo.isPresent()) {
			// メール情報が登録されている場合
			mergeMailInfo(dto, befMailInfo.get());

			// メール情報を更新する
			mailInfoUpdateService.update(befMailInfo.get());
		} else {
			// メール情報が登録されてない場合
			MailInfo mailInfo = new MailInfo();

			mergeMailInfo(dto, mailInfo);

			// メール情報を新規登録する
			mailInfoCreateService.create(mailInfo);
		}

		// 健康情報ファイル設定情報を検索し、Dtoの内容をマージする
		HealthInfoFileSetting healthInfoFileSetting = healthInfoFileSettingSearchService.findByUserId(dto.getUserId())
				.get();
		mergeHealthInfoFileSetting(dto, healthInfoFileSetting);
		// 健康情報ファイル設定情報を更新する
		healthInfoFileSettingUpdateService.update(healthInfoFileSetting);

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

		BeanUtil.copy(dto, account, Arrays.asList("userId"), (src, dest) -> {
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

		BeanUtil.copy(dto, mailInfo);

	}

	/**
	 * アカウントDTOを健康情報ファイル設定情報にマージする
	 *
	 * @param dto
	 *     アカウントDTO
	 * @param healthInfoFileSetting
	 *     健康情報ファイル設定情報
	 */
	private void mergeHealthInfoFileSetting(AccountDto dto, HealthInfoFileSetting healthInfoFileSetting) {

		BeanUtil.copy(dto, healthInfoFileSetting, Arrays.asList("userId"));
	}

}
