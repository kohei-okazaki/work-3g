package jp.co.ha.dashboard.controller;

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

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.business.interceptor.annotation.CsrfToken;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.form.HealthInfoFileSettingForm;
import jp.co.ha.dashboard.service.HealthInfoFileSettingService;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.controller.BaseWizardController;

/**
 * 健康管理_健康情報ファイル設定コントローラ
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
	 * Formを返す
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return HealthInfoFileSettingForm
	 * @throws BaseException
	 *     基底例外
	 */
	@ModelAttribute("healthInfoFileSettingForm")
	public HealthInfoFileSettingForm setUpForm(HttpServletRequest request) throws BaseException {

		// セッションからユーザIDを取得
		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();

		// 健康情報ファイル設定を取得
		HealthInfoFileSetting entity = healthInfoFileSettingSearchService.findByUserId(userId);
		HealthInfoFileSettingForm form = new HealthInfoFileSettingForm();
		if (BeanUtil.isNull(entity)) {
			// 健康情報ファイル設定が未登録の場合
			form.setUserId(userId);
			form.setHeaderFlag(CommonFlag.TRUE.getValue());
			form.setFooterFlag(CommonFlag.TRUE.getValue());
			form.setMaskFlag(CommonFlag.TRUE.getValue());
			form.setEnclosureCharFlag(CommonFlag.TRUE.getValue());
		} else {
			BeanUtil.copy(entity, form);
		}
		return form;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/input")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(DashboardView.HEALTH_INFO_FILE_SETTING_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CsrfToken(factocy = true)
	@PostMapping(value = "/confirm")
	public String confirm(Model model, @Valid HealthInfoFileSettingForm form, BindingResult result,
			HttpServletRequest request) throws BaseException {

		if (result.hasErrors()) {
			return getView(DashboardView.HEALTH_INFO_FILE_SETTING_INPUT);
		}

		model.addAttribute("form", form);
		return getView(DashboardView.HEALTH_INFO_FILE_SETTING_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CsrfToken(check = true)
	@PostMapping(value = "/complete")
	public String complete(Model model, HealthInfoFileSettingForm form, HttpServletRequest request)
			throws BaseException {

		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();
		if (!userId.equals(form.getUserId())) {
			throw new SystemException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}

		healthInfoFileSettingService.execute(form);

		return getView(DashboardView.HEALTH_INFO_FILE_SETTING_COMPLETE);
	}

}
