package jp.co.ha.web.controller;

import java.util.List;

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

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.business.find.MailInfoSearchService;
import jp.co.ha.common.entity.Account;
import jp.co.ha.common.entity.MailInfo;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.AccountSettingForm;
import jp.co.ha.web.service.AccountSettingService;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_アカウント設定コントローラ<br>
 *
 */
@Controller
public class AccountSettingController implements BaseWizardController<AccountSettingForm> {

	/** アカウント設定サービス */
	@Autowired
	private AccountSettingService accountSettingService;

	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** メール情報検索サービス */
	@Autowired
	private MailInfoSearchService mailInfoSearchService;
	/** session管理サービス */
	@Autowired
	private SessionManageService sessionService;

	/**
	 * Validateを設定<br>
	 *
	 * @param binder
	 *            WebDataBinder
	 */
	@Override
	@InitBinder(value = "accountSettingForm")
	public void initBinder(WebDataBinder binder) {
//		binder.setValidator(new AccountSettingValidator());
	}

	/**
	 * Formを返す<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@ModelAttribute
	public AccountSettingForm setUpForm(HttpServletRequest request) {

		// セッションからユーザIDを取得
		String userId = sessionService.getValue(request.getSession(), "userId", String.class);

		// アカウント情報を検索
		Account account = accountSearchService.findByUserId(userId);
		// メール情報を検索
		MailInfo mailInfo = mailInfoSearchService.findByUserId(userId);

		AccountSettingForm accountSettingForm = new AccountSettingForm();
		BeanUtil.copy(account, accountSettingForm);
		if (BeanUtil.notNull(mailInfo)) {
			BeanUtil.copy(mailInfo, accountSettingForm, List.of("userId"));
		}

		return accountSettingForm;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/account-setting-input.html")
	public String input(Model model, HttpServletRequest request) throws BaseAppException {
		return getView(ManageWebView.ACCOUNT_SETTING_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/account-setting-confirm.html")
	public String confirm(Model model, @Valid AccountSettingForm form, BindingResult result) throws BaseAppException {

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
	public String complete(Model model, AccountSettingForm form, HttpServletRequest request) throws BaseAppException {

		// form情報から更新処理を行う
		accountSettingService.execute(form);

		return getView(ManageWebView.ACCOUNT_SETTING_COMPLETE);
	}

}
