package jp.co.ha.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.find.AccountSearchService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseWebController;
import jp.co.ha.web.form.LoginForm;
import jp.co.ha.web.validator.LoginValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * ログインコントローラ<br>
 *
 */
@Controller
@RequestMapping("login")
public class LoginController implements BaseWebController {

	/** sessionサービス */
	@Autowired
	private SessionManageService sessionService;
	@Autowired
	private MessageSource messageSource;
	/** アカウント検索サービス */
	@Autowired
	private AccountSearchService accountSearchService;

	/**
	 * Validateを設定<br>
	 *
	 * @param binder
	 *     WebDataBinder
	 */
	@InitBinder("loginForm")
	public void initBinder(WebDataBinder binder) {
		LoginValidator validator = new LoginValidator();
		validator.setAccountSearchService(accountSearchService);
		binder.addValidators(validator);
	}

	/**
	 * Formを返す<br>
	 *
	 * @return
	 */
	@ModelAttribute
	public LoginForm setUpForm() {
		return new LoginForm();
	}

	/**
	 * ログイン画面
	 *
	 * @param model
	 *     Model
	 * @param request
	 *     HttpServletRequest
	 * @return ログイン画面
	 */
	@GetMapping("/index.html")
	public String index(Model model, HttpServletRequest request) {
		// sessionに格納している情報をすべて削除する
		sessionService.removeValues(request.getSession());
		System.out.println(messageSource.getMessage("message", null, Locale.JAPANESE));
		return getView(ManageWebView.LOGIN);
	}

	/**
	 * TOP画面
	 *
	 * @param model
	 *     Model
	 * @param request
	 *     HttpServletRequest
	 * @param loginForm
	 *     LoginForm
	 * @param result
	 *     BindingResult
	 * @return
	 */
	@PostMapping("/top.html")
	public String top(Model model, HttpServletRequest request, @Valid LoginForm loginForm, BindingResult result) {

		if (result.hasErrors()) {
			// validationエラーの場合
			return getView(ManageWebView.LOGIN);
		}

		// セッションにユーザIDを登録する。
		sessionService.setValue(request.getSession(), "userId", loginForm.getUserId());

		return getView(ManageWebView.TOP);

	}

	/**
	 * TOP画面
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return
	 */
	@GetMapping("/top.html")
	public String top(HttpServletRequest request) {

		String userId = sessionService.getValue(request.getSession(), "userId", String.class);
		return getView(StringUtil.isEmpty(userId) ? ManageWebView.LOGIN : ManageWebView.TOP);
	}
}
