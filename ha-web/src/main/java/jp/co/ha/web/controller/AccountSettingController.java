package jp.co.ha.web.controller;

import java.util.List;

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
import jp.co.ha.business.db.crud.read.MailInfoSearchService;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.type.DateFormatType;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.MailInfo;
import jp.co.ha.web.form.AccountSettingForm;
import jp.co.ha.web.service.AccountSettingService;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_アカウント設定コントローラ
 *
 */
@Controller
@RequestMapping("accountSetting")
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
	 * Formを返す
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return AccountSettingForm
	 * @throws BaseException
	 *     基底例外
	 */
	@ModelAttribute("accountSettingForm")
	public AccountSettingForm setUpForm(HttpServletRequest request) throws BaseException {

		// セッションからユーザIDを取得
		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();

		// アカウント情報を検索
		Account account = accountSearchService.findByUserId(userId);
		// メール情報を検索
		MailInfo mailInfo = mailInfoSearchService.findByUserId(userId);

		AccountSettingForm accountSettingForm = new AccountSettingForm();
		BeanUtil.copy(account, accountSettingForm);
		accountSettingForm.setPasswordExpire(DateUtil.toString(account.getPasswordExpire(), DateFormatType.YYYYMMDD));
		if (BeanUtil.notNull(mailInfo)) {
			BeanUtil.copy(mailInfo, accountSettingForm, List.of("userId"));
		}

		return accountSettingForm;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/input.html")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(ManageWebView.ACCOUNT_SETTING_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/confirm.html")
	public String confirm(Model model, @Valid AccountSettingForm form, BindingResult result, HttpServletRequest request) throws BaseException {

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
	@PostMapping(value = "/complete.html")
	public String complete(Model model, AccountSettingForm form, HttpServletRequest request) throws BaseException {

		// form情報から更新処理を行う
		accountSettingService.execute(form);

		return getView(ManageWebView.ACCOUNT_SETTING_COMPLETE);
	}

}
