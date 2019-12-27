package jp.co.ha.dashboard.login.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.business.login.LoginCheck;
import jp.co.ha.business.login.LoginCheckResult;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.dashboard.login.form.LoginForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_ログイン画面コントローラ
 *
 * @since 1.0
 */
@Controller
@RequestMapping("login")
public class LoginController implements BaseWebController {

	/** sessionサービス */
	@Autowired
	private SessionManageService sessionService;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;
	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** MessageSource */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Formを返す
	 *
	 * @return LoginForm
	 */
	@ModelAttribute("loginForm")
	public LoginForm setUpForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return ログイン画面
	 */
	@NonAuth
	@GetMapping("/index")
	public String index(HttpServletRequest request) {
		sessionService.removeValues(request.getSession());
		return getView(DashboardView.LOGIN);
	}

	/**
	 * TOP画面
	 *
	 * @param model
	 *     Model
	 * @param request
	 *     HttpServletRequest
	 * @param form
	 *     LoginForm
	 * @param result
	 *     BindingResult
	 * @return TOP画面
	 * @throws BaseException
	 *     基底例外
	 */
	@NonAuth
	@PostMapping("/top")
	public String top(Model model, HttpServletRequest request, @Valid LoginForm form, BindingResult result)
			throws BaseException {

		if (result.hasErrors()) {
			// validationエラーの場合
			return getView(DashboardView.LOGIN);
		}

		// アカウント情報を検索
		Optional<Account> account = accountSearchService.findByUserId(form.getUserId());
		LoginCheckResult checkResult = new LoginCheck().check(account, form.getPassword());
		if (checkResult.hasError()) {
			String errorMessage = messageSource.getMessage(checkResult.getErrorCode().getOuterErrorCode(), null,
					Locale.getDefault());
			model.addAttribute("errorMessage", errorMessage);
			return getView(DashboardView.LOGIN);
		}

		// セッションにユーザIDを登録する。
		sessionService.setValue(request.getSession(), "userId", form.getUserId());

		// 健康情報を検索する
		SelectOption selectOption = new SelectOption().put("HEALTH_INFO_REG_DATE", SortType.ASC).setLimit(10);
		List<HealthInfo> list = healthInfoSearchService.findByUserIdForDashboard(form.getUserId(), selectOption);
		if (list == null) {
			LoggerFactory.getLogger(this.getClass()).info("検索結果なし");
		} else {
			list.stream().forEach(e -> LoggerFactory.getLogger(this.getClass()).infoRes(e));
		}
		return getView(DashboardView.TOP);

	}

	/**
	 * TOP画面
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return TOP画面
	 */
	@NonAuth
	@GetMapping("/top")
	public String top(HttpServletRequest request) {
		// jp.co.ha.business.interceptor.DashboardAuthInterceptorで認証チェックを行うと、
		// ログイン前のアカウント作成画面でヘッダーを踏んだときにログイン情報がなくてコケるのでここでsession情報をチェックする
		String userId = sessionService.getValue(request.getSession(), "userId", String.class).orElse(null);
		return getView(StringUtil.isEmpty(userId) ? DashboardView.LOGIN : DashboardView.TOP);
	}
}
