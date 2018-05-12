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

	/**
	 * Validateを設定<br>
	 * @param binder
	 */
	@InitBinder(value = "LoginForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new LoginValidator());
	}

	/**
	 * ログイン画面
	 * @param model
	 * @param request
	 * @return ログイン画面
	 */
	@GetMapping("/login.html")
	public String login(Model model, HttpServletRequest request) {
		return getView(ManageWebView.LOGIN);
	}

	/**
	 * メニュー画面
	 * @param model
	 * @param request
	 * @param loginForm
	 * @param result
	 * @return
	 */
	@PostMapping("/menu.html")
	public String menu(Model model, HttpServletRequest request, @Valid LoginForm loginForm, BindingResult result) {

		if (result.hasErrors()) {
			// バインドエラー時の処理
			System.out.println("バインドエラーが発生");
			return getView(ManageWebView.LOGIN);
		}

		if (!loginService.existAccount(loginForm)) {
			// アカウント情報を取得出来なかった場合
			model.addAttribute("errorMessage", "アカウントが存在しません。");
			getView(ManageWebView.LOGIN);
		}

		if (loginService.invalidPassword(loginForm)) {
			// 入力されたユーザIDと紐付くアカウント情報.パスワードと入力情報.パスワードが異なる場合
			model.addAttribute("errorMessage", "IDとパスワードが一致しません。");
			getView(ManageWebView.LOGIN);
		}

		if (loginService.invalidAccount(loginForm)) {
			// アカウント情報が有効期限切の場合
			model.addAttribute("errorMessage", "アカウントは有効期限切れです。");
			getView(ManageWebView.LOGIN);
		}

		// セッションにユーザIDを登録する。
		this.loginService.registSession(request.getSession(), loginForm.getUserId());

		return getView(ManageWebView.MENU);

	}

	@GetMapping("/menu.html")
	public String menu() {
		return getView(ManageWebView.LOGIN);
	}
}
