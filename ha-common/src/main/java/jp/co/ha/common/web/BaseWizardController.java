package jp.co.ha.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import jp.co.ha.common.exception.BaseAppException;

/**
 * ウィザード型式の基底コントローラ<br>
 * 全てのウィザード形式コントローラクラスはこのクラスを継承すること。<br>
 *
 * @param <F>
 *     対象formクラス
 */
public interface BaseWizardController<F extends BaseForm> extends BaseWebController {

	/**
	 * Validateを設定<br>
	 *
	 * @param binder
	 *     WebDataBinder
	 */
	void initBinder(WebDataBinder binder);

	/**
	 * 入力画面
	 *
	 * @param model
	 *     Model
	 * @param request
	 *     HttpServletRequest
	 * @return
	 * @throws BaseAppException
	 *     例外クラス
	 */
	String input(Model model, HttpServletRequest request) throws BaseAppException;

	/**
	 * 確認画面
	 *
	 * @param model
	 *     Model
	 * @param form
	 *     F extends BaseForm
	 * @param result
	 *     BindingResult
	 * @return
	 * @throws BaseAppException
	 *     例外クラス
	 */
	String confirm(Model model, @Valid F form, BindingResult result) throws BaseAppException;

	/**
	 * 完了画面
	 *
	 * @param model
	 *     Model
	 * @param form
	 *     F extends BaseForm
	 * @param request
	 *     HttpServletRequest
	 * @return
	 * @throws BaseAppException
	 *     例外クラス
	 */
	String complete(Model model, F form, HttpServletRequest request) throws BaseAppException;

}
