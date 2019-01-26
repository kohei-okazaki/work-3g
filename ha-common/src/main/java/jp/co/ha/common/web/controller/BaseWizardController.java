package jp.co.ha.common.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.web.form.BaseForm;

/**
 * ウィザード型式の基底コントローラ<br>
 * 全てのウィザード形式コントローラクラスはこのクラスを継承すること。<br>
 *
 * @param <F>
 *     対象formクラス
 */
public interface BaseWizardController<F extends BaseForm> extends BaseWebController {

	/**
	 * 入力画面
	 *
	 * @param model
	 *     Model
	 * @param request
	 *     HttpServletRequest
	 * @return view名
	 * @throws BaseException
	 *     基底例外
	 */
	String input(Model model, HttpServletRequest request) throws BaseException;

	/**
	 * 確認画面
	 *
	 * @param model
	 *     Model
	 * @param form
	 *     F extends BaseForm
	 * @param result
	 *     BindingResult
	 * @param request
	 *     HttpServletRequest
	 * @return view名
	 * @throws BaseException
	 *     基底例外
	 */
	String confirm(Model model, @Valid F form, BindingResult result, HttpServletRequest request) throws BaseException;

	/**
	 * 完了画面
	 *
	 * @param model
	 *     Model
	 * @param form
	 *     F extends BaseForm
	 * @param request
	 *     HttpServletRequest
	 * @return view名
	 * @throws BaseException
	 *     基底例外
	 */
	String complete(Model model, F form, HttpServletRequest request) throws BaseException;

}
