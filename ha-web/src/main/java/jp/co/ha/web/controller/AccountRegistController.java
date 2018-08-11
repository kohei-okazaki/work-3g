package jp.co.ha.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.db.create.AccountCreateService;
import jp.co.ha.business.db.create.HealthInfoFileSettingCreateService;
import jp.co.ha.business.db.entity.Account;
import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.business.db.find.AccountSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.annotation.NonAuth;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.AccountRegistForm;
import jp.co.ha.web.service.AccountRegistService;
import jp.co.ha.web.validator.AccountRegistValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_アカウント登録画面コントローラ<br>
 *
 */
@Controller
@RequestMapping(value = "accountRegist")
public class AccountRegistController implements BaseWizardController<AccountRegistForm> {

	/** アカウント登録画面サービス */
	@Autowired
	private AccountRegistService service;
	/** アカウント作成サービス */
	@Autowired
	private AccountCreateService accountCreateService;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** 健康情報ファイル設定作成サービス */
	@Autowired
	private HealthInfoFileSettingCreateService healthInfoFileSettingCreateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder("accountRegistForm")
	public void initBinder(WebDataBinder binder) {
		AccountRegistValidator validator = new AccountRegistValidator();
		validator.setAccountSearchService(accountSearchService);
		binder.addValidators(validator);
	}

	/**
	 * Formを返す<br>
	 *
	 * @return
	 */
	@ModelAttribute
	public AccountRegistForm setUpForm() {
		return new AccountRegistForm();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonAuth
	@GetMapping(value = "/input.html")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(ManageWebView.ACCOUNT_REGIST_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonAuth
	@PostMapping(value = "/confirm.html")
	public String confirm(Model model, @Valid AccountRegistForm form, BindingResult result)
			throws BaseException {

		if (result.hasErrors()) {
			// validatationエラーの場合
			return getView(ManageWebView.ACCOUNT_REGIST_INPUT);
		}

		model.addAttribute("form", form);

		return getView(ManageWebView.ACCOUNT_REGIST_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonAuth
	@PostMapping(value = "/complete.html")
	public String complete(Model model, AccountRegistForm form, HttpServletRequest request)
			throws BaseException {

		// アカウントEntityに変換
		Account account = service.toAccount(form);
		// アカウントを作成
		accountCreateService.create(account);

		// 健康情報ファイル設定情報Entityに変換
		HealthInfoFileSetting healthInfoFileSetting = service.toHealthInfoFileSetting(form);
		// 健康情報ファイル設定情報を作成
		healthInfoFileSettingCreateService.create(healthInfoFileSetting);

		return getView(ManageWebView.ACCOUNT_REGIST_COMPLETE);
	}

}
