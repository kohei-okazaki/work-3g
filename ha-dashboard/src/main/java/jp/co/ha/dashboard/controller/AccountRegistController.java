package jp.co.ha.dashboard.controller;

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
import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.business.interceptor.annotation.CsrfToken;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.form.AccountRegistForm;
import jp.co.ha.dashboard.service.AccountRegistService;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.Account;
import jp.co.ha.web.controller.BaseWizardController;

/**
 * 健康管理_アカウント登録画面コントローラ
 *
 */
@Controller
@RequestMapping("accountRegist")
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
	@GetMapping(value = "/input")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(DashboardView.ACCOUNT_REGIST_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonAuth
	@CsrfToken(factocy = true)
	@PostMapping(value = "/confirm")
	public String confirm(Model model, @Valid AccountRegistForm form, BindingResult result, HttpServletRequest request)
			throws BaseException {

		if (result.hasErrors()) {
			return getView(DashboardView.ACCOUNT_REGIST_INPUT);
		}

		Account account = accountSearchService.findByUserId(form.getUserId());
		if (BeanUtil.notNull(account)) {
			model.addAttribute("errorMessage", "指定されたユーザIDは既にアカウント情報が登録されています");
			LOG.warn("指定されたユーザIDは既にアカウント情報が登録されています userId:" + form.getUserId());
			return getView(DashboardView.ACCOUNT_REGIST_INPUT);
		}

		model.addAttribute("form", form);

		return getView(DashboardView.ACCOUNT_REGIST_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@NonAuth
	@CsrfToken(check = true)
	@PostMapping(value = "/complete")
	public String complete(Model model, AccountRegistForm form, HttpServletRequest request) throws BaseException {

		AccountDto dto = new AccountDto();
		BeanUtil.copy(form, dto);
		// 登録処理を行う
		accountRegistService.regist(dto);

		return getView(DashboardView.ACCOUNT_REGIST_COMPLETE);
	}

}
