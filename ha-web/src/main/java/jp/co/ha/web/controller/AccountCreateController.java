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
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.ha.common.entity.Account;
import jp.co.ha.common.exception.AccountCreateException;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.AccountCreateForm;
import jp.co.ha.web.service.AccountCreateService;
import jp.co.ha.web.validator.AccountCreateValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_アカウント作成コントローラ<br>
 *
 */
@Controller
public class AccountCreateController implements BaseWizardController<AccountCreateForm, AccountCreateException> {

	/** アカウント作成サービス */
	@Autowired
	private AccountCreateService accountCreateService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder("AccountCreateForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new AccountCreateValidator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "account-create-input.html")
	public String input(Model model, HttpServletRequest request) throws AccountCreateException {
		return getView(ManageWebView.ACCOUNT_CREATE_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/account-create-confirm.html")
	public String confirm(Model model, @Valid AccountCreateForm form, BindingResult result)
			throws AccountCreateException {

		if (result.hasErrors()) {
			// validatationエラーの場合
			return getView(ManageWebView.ACCOUNT_CREATE_INPUT);
		}

		model.addAttribute("form", form);

		return getView(ManageWebView.ACCOUNT_CREATE_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/account-create-complete.html")
	public String complete(Model model, AccountCreateForm form, HttpServletRequest request)
			throws AccountCreateException {

		// アカウントEntityに変換する
		Account account = accountCreateService.toAccount(form);

		// アカウントを作成する
		accountCreateService.create(account);

		return getView(ManageWebView.ACCOUNT_CREATE_COMPLETE);
	}

}
