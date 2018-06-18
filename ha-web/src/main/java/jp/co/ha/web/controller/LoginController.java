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

import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.BaseWebController;
import jp.co.ha.web.form.LoginForm;
import jp.co.ha.web.service.LoginService;
import jp.co.ha.web.validator.LoginValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * ログインコントローラ<br>
 *
 */
@Controller
public class LoginController implements BaseWebController {

	/** ログインサービス */
	@Autowired
	private LoginService loginService;
	/** sessionサービス */
	@Autowired
	private SessionManageService sessionService;
	@Autowired
	private MessageSource messageSource;

	/**
	 * Validateを設定<br>
	 *
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder(value = "loginForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new LoginValidator());
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
	 *            Model
	 * @param request
	 *            HttpServletRequest
	 * @return ログイン画面
	 */
	@GetMapping("/login.html")
	public String login(Model model, HttpServletRequest request) {
		// sessionに格納している情報をすべて削除する
		sessionService.removeValues(request);
		System.out.println(messageSource.getMessage("message", null, Locale.JAPANESE));
		return getView(ManageWebView.LOGIN);
	}

	/**
	 * メニュー画面
	 *
	 * @param model
	 *            Model
	 * @param request
	 *            HttpServletRequest
	 * @param loginForm
	 *            LoginForm
	 * @param result
	 *            BindingResult
	 * @return
	 */
	@PostMapping("/menu.html")
	public String menu(Model model, HttpServletRequest request, @Valid LoginForm loginForm, BindingResult result) {

		if (result.hasErrors()) {
			// バインドエラー時の処理
			System.out.println("バインドエラーが発生");
			return getView(ManageWebView.LOGIN);
		}

		if (loginService.noExistAccount(loginForm)) {
			// アカウント情報を取得出来なかった場合
			model.addAttribute("errorMessage", "アカウントが存在しません。");
			return getView(ManageWebView.LOGIN);
		}

		if (loginService.invalidPassword(loginForm)) {
			// 入力されたユーザIDと紐付くアカウント情報.パスワードと入力情報.パスワードが異なる場合
			model.addAttribute("errorMessage", "IDとパスワードが一致しません。");
			return getView(ManageWebView.LOGIN);
		}

		if (loginService.invalidAccount(loginForm)) {
			// アカウント情報が有効期限切の場合
			model.addAttribute("errorMessage", "アカウントは有効期限切れです。");
			return getView(ManageWebView.LOGIN);
		}

		// セッションにユーザIDを登録する。
		sessionService.setValue(request, "userId", loginForm.getUserId());

		return getView(ManageWebView.MENU);

	}

	/**
	 * メニュー画面に遷移<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	@GetMapping("/menu.html")
	public String menu(HttpServletRequest request) {

		String userId = sessionService.getValue(request, "userId", String.class);
		return getView(BeanUtil.isNull(userId) ? ManageWebView.LOGIN : ManageWebView.MENU);
	}
}
