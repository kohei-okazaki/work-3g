package jp.co.ha.web.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.MailInfo;
import jp.co.ha.common.exception.AccountSettingException;
import jp.co.ha.common.service.AccountSearchService;
import jp.co.ha.common.service.MailInfoSearchService;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.AccountSettingForm;
import jp.co.ha.web.service.AccountSettingService;
import jp.co.ha.web.service.MailInfoCreateService;
import jp.co.ha.web.validator.AccountSettingValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_アカウント設定コントローラ<br>
 *
 */
@Controller
public class AccountSettingController implements BaseWizardController<AccountSettingForm, AccountSettingException> {

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** アカウント設定サービス */
	@Autowired
	private AccountSettingService accountSettingService;
	/** メール情報検索サービス */
	@Autowired
	private MailInfoSearchService mailInfoSearchService;
	/** メール情報作成サービス */
	@Autowired
	private MailInfoCreateService mailInfoCreateService;

	/**
	 * Validateを設定<br>
	 * @param binder
	 */
	@Override
	@InitBinder(value = "AccountSettingForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new AccountSettingValidator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/account-setting-input.html")
	public String input(Model model, HttpServletRequest request) throws AccountSettingException {

		// セッションからユーザIDを取得
		String userId = (String) request.getSession().getAttribute("userId");

		// アカウント情報を検索
		Account account = accountSearchService.findAccountByUserId(userId);
		model.addAttribute("account", account);

		// メール情報を検索
		MailInfo mailInfo = mailInfoSearchService.findMailInfoByUserId(userId);
		model.addAttribute("mailInfo", mailInfo);

		return getView(ManageWebView.ACCOUNT_SETTING_INPUT);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/account-setting-confirm.html")
	public String confirm(Model model, @Valid AccountSettingForm form, BindingResult result) throws AccountSettingException {

		if (result.hasErrors()) {
			return getView(ManageWebView.ACCOUNT_SETTING_INPUT);
		}

		model.addAttribute("form", form);

		return getView(ManageWebView.ACCOUNT_SETTING_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/account-setting-complete.html")
	public String complete(Model model, AccountSettingForm form, HttpServletRequest request) throws AccountSettingException {

//		if (CodeManager.getInstance().isEquals(MainKey.FLAG, SubKey.TRUE, form.getDeleteFlag())) {
//			// アカウントを削除する場合
//			this.accountSettingService.deleteAccount(form);
//		}

		// アカウント情報にマージ
		Account befAccount = accountSearchService.findAccountByUserId(form.getUserId());
		this.accountSettingService.mergeAccount(befAccount, form);

		// メール情報を検索
		MailInfo befMailInfo = mailInfoSearchService.findMailInfoByUserId(form.getUserId());
		if (Objects.isNull(befMailInfo.getUserId())) {

			MailInfo mailInfo = accountSettingService.convertMailInfo(form);
			// メール情報を新規登録する
			mailInfoCreateService.create(mailInfo);
			// アカウント情報を更新する
			accountSettingService.updateAccount(befAccount);

		} else {

			accountSettingService.mergeMailInfo(befMailInfo, form);
			// 更新処理を行う
			accountSettingService.update(befAccount, befMailInfo);

		}

		return getView(ManageWebView.ACCOUNT_SETTING_COMPLETE);
	}

}
