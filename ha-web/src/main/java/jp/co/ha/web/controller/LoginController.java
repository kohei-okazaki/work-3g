package jp.co.ha.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import jp.co.ha.common.web.BaseWebController;
import jp.co.ha.web.service.LoginService;
import jp.co.ha.web.validator.LoginValidator;

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

}
