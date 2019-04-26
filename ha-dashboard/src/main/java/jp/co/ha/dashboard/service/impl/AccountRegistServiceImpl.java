package jp.co.ha.dashboard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ha.business.db.crud.create.AccountCreateService;
import jp.co.ha.business.db.crud.create.HealthInfoFileSettingCreateService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.HashEncoder;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.dashboard.form.AccountRegistForm;
import jp.co.ha.dashboard.service.AccountRegistService;
import jp.co.ha.dashboard.service.annotation.Sha256;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * アカウント作成サービス実装クラス
 *
 */
@Service
public class AccountRegistServiceImpl implements AccountRegistService {

	/** アカウント作成サービス */
	@Autowired
	private AccountCreateService accountCreateService;
	/** 健康情報ファイル設定作成サービス */
	@Autowired
	private HealthInfoFileSettingCreateService healthInfoFileSettingCreateService;
	/** パスワード作成サービス */
	@Sha256
	@Autowired
	private HashEncoder encoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void regist(AccountRegistForm form) throws BaseException {
		// アカウント情報を作成
		accountCreateService.create(toAccount(form));
		// 健康情報ファイル設定情報を作成
		healthInfoFileSettingCreateService.create(toHealthInfoFileSetting(form));
	}

	/**
	 * アカウント情報に変換する
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @return アカウント情報Entity
	 * @throws BaseException
	 *     基底例外
	 */
	private Account toAccount(AccountRegistForm form) throws BaseException {
		Account account = new Account();
		BeanUtil.copy(form, account);
		account.setDeleteFlag(CommonFlag.FALSE.getValue());
		account.setPasswordExpire(DateUtil.addMonth(DateUtil.getSysDate(), 6));
		account.setApiKey(encoder.encode(form.getPassword(), form.getUserId()));
		return account;
	}

	/**
	 * 健康情報ファイル設定に変換する
	 *
	 * @param form
	 *     アカウント登録画面フォーム
	 * @return 健康情報ファイル設定
	 */
	private HealthInfoFileSetting toHealthInfoFileSetting(AccountRegistForm form) {
		HealthInfoFileSetting entity = new HealthInfoFileSetting();
		entity.setUserId(form.getUserId());
		entity.setEnclosureCharFlag(CommonFlag.FALSE.getValue());
		entity.setHeaderFlag(CommonFlag.FALSE.getValue());
		entity.setFooterFlag(CommonFlag.FALSE.getValue());
		entity.setMaskFlag(CommonFlag.FALSE.getValue());
		return entity;
	}

}
