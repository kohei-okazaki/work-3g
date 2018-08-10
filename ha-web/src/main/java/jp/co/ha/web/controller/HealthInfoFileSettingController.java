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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.db.entity.HealthInfoFileSetting;
import jp.co.ha.business.db.find.HealthInfoFileSettingSearchService;
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.HealthInfoFileSettingForm;
import jp.co.ha.web.service.HealthInfoFileSettingService;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報ファイル設定コントローラ<br>
 *
 */
@Controller
@RequestMapping("healthInfoFileSetting")
public class HealthInfoFileSettingController implements BaseWizardController<HealthInfoFileSettingForm> {

	/** 健康情報ファイル設定サービス */
	@Autowired
	private HealthInfoFileSettingService healthInfoFileSettingService;
	/** session管理サービス */
	@Autowired
	private SessionManageService sessionService;
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder(value = "healthInfoFileSettingForm")
	public void initBinder(WebDataBinder binder) {
	}

	/**
	 * Formを返す<br>
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return
	 * @throws BaseException
	 */
	@ModelAttribute
	public HealthInfoFileSettingForm setUpForm(HttpServletRequest request) throws BaseException {

		// セッションからユーザIDを取得
		String userId = sessionService.getValue(request.getSession(), "userId", String.class);
		if (BeanUtil.isNull(userId)) {
			throw new HealthInfoException(ErrorCode.ILLEGAL_ACCESS_ERROR, "session内のユーザIDが不正です");
		}
		// 健康情報ファイル設定を取得
		HealthInfoFileSetting entity = healthInfoFileSettingSearchService.findByUserId(userId);
		HealthInfoFileSettingForm form = new HealthInfoFileSettingForm();
		if (BeanUtil.isNull(entity)) {
			form.setUserId(userId);
			form.setHeaderFlag(null);
			form.setFooterFlag(null);
			form.setMaskFlag(null);
			form.setEnclosureCharFlag(null);
		} else {
			BeanUtil.copy(entity, form);
		}
		return form;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/input.html")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(ManageWebView.HEALTH_INFO_FILE_SETTING_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/confirm.html")
	public String confirm(Model model, @Valid HealthInfoFileSettingForm form, BindingResult result) throws BaseException {
		if (result.hasErrors()) {
			return getView(ManageWebView.HEALTH_INFO_FILE_SETTING_INPUT);
		}

		model.addAttribute("form", form);
		return getView(ManageWebView.HEALTH_INFO_FILE_SETTING_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/complete.html")
	public String complete(Model model, HealthInfoFileSettingForm form, HttpServletRequest request) throws BaseException {

		healthInfoFileSettingService.execute(form);

		return getView(ManageWebView.HEALTH_INFO_FILE_SETTING_COMPLETE);
	}

}
