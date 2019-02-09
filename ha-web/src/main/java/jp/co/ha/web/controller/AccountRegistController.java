package jp.co.ha.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.db.entity.Account;
import jp.co.ha.web.form.AccountRegistForm;
import jp.co.ha.web.service.AccountRegistService;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_アカウント登録画面コントローラ
 *
 */
@Controller
@RequestMapping(value = "accountRegist")
public class AccountRegistController implements BaseWizardController<AccountRegistForm> {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(AccountRegistController.class);
	/** アカウント登録画面サービス */
	@Autowired
	private AccountRegistService accountRegistService;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * Formを返す
	 *
	 * @return AccountRegistForm
	 */
	@ModelAttribute("accountRegistForm")
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
	public String confirm(Model model, @Valid AccountRegistForm form, BindingResult result, HttpServletRequest request)
			throws BaseException {

		if (result.hasErrors()) {
			// validatationエラーの場合
			return getView(ManageWebView.ACCOUNT_REGIST_INPUT);
		}

		Account account = accountSearchService.findByUserId(form.getUserId());
		if (BeanUtil.notNull(account)) {
			model.addAttribute("errorMessage", "アカウント情報が既に登録されています");
			LOG.warn("アカウント情報が既に登録されています");
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
	public String complete(Model model, AccountRegistForm form, HttpServletRequest request) throws BaseException {

		// 登録処理を行う
		accountRegistService.regist(form);

		return getView(ManageWebView.ACCOUNT_REGIST_COMPLETE);
	}

}
