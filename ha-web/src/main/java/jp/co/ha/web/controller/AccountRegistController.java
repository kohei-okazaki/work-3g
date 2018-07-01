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

import jp.co.ha.business.create.AccountCreateService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.AccountRegistForm;
import jp.co.ha.web.service.AccountRegistService;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_アカウント登録画面コントローラ<br>
 *
 */
@Controller
public class AccountRegistController implements BaseWizardController<AccountRegistForm> {

	/** アカウント登録画面サービス */
	@Autowired
	private AccountRegistService service;
	/** アカウント作成サービス */
	@Autowired
	private AccountCreateService accountCreateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder("accountRegistForm")
	public void initBinder(WebDataBinder binder) {
		//		binder.setValidator(new AccountRegistValidator());
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
	@GetMapping(value = "account-regist-input.html")
	public String input(Model model, HttpServletRequest request) throws BaseAppException {
		return getView(ManageWebView.ACCOUNT_REGIST_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/account-regist-confirm.html")
	public String confirm(Model model, @Valid AccountRegistForm form, BindingResult result)
			throws BaseAppException {

		if (result.hasErrors()) {
			// validatationエラーの場合
			return getView(ManageWebView.ACCOUNT_REGIST_INPUT);
		}

		if (service.invalidUserId(form)) {
			model.addAttribute("errorMessage", "アカウントは既に登録されています");
			return getView(ManageWebView.ACCOUNT_REGIST_INPUT);
		}

		model.addAttribute("form", form);

		return getView(ManageWebView.ACCOUNT_REGIST_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/account-regist-complete.html")
	public String complete(Model model, AccountRegistForm form, HttpServletRequest request)
			throws BaseAppException {

		// アカウントEntityに変換する
		Account account = service.toAccount(form);

		// アカウントを作成する
		accountCreateService.create(account);

		return getView(ManageWebView.ACCOUNT_REGIST_COMPLETE);
	}

}
