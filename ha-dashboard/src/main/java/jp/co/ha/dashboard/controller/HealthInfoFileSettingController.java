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
import jp.co.ha.business.dto.HealthInfoFileSettingDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.CsrfToken;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.form.HealthInfoFileSettingForm;
import jp.co.ha.dashboard.service.HealthInfoFileSettingService;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.controller.BaseWizardController;

/**
 * 健康管理_健康情報ファイル設定コントローラ
 *
 * @since 1.0
 */
@Controller
@RequestMapping("healthinfofilesetting")
public class HealthInfoFileSettingController implements BaseWizardController<HealthInfoFileSettingForm> {

	/** 健康情報ファイル設定サービス */
	@Autowired
	private HealthInfoFileSettingService healthInfoFileSettingService;
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;
	/** session管理サービス */
	@Autowired
	private SessionManageService sessionManagerService;

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
		String userId = sessionManagerService.getValue(request.getSession(), "userId", String.class).get();

		// 健康情報ファイル設定を取得
		HealthInfoFileSetting entity = healthInfoFileSettingSearchService.findByUserId(userId).get();
		HealthInfoFileSettingForm form = new HealthInfoFileSettingForm();
		BeanUtil.copy(entity, form);

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

		// sessionに健康情報ファイル設定Form情報を保持
		sessionManagerService.setValue(request.getSession(), "healthInfoFileSettingForm", form);

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

		// sessionより健康情報ファイル設定Form情報を取得
		HealthInfoFileSettingForm healthInfoFileSettingForm = sessionManagerService
				.getValue(request.getSession(), "healthInfoFileSettingForm", HealthInfoFileSettingForm.class)
				.orElseThrow(() -> new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

		HealthInfoFileSettingDto dto = new HealthInfoFileSettingDto();
		BeanUtil.copy(healthInfoFileSettingForm, dto);

		// Form情報から更新処理を行う
		healthInfoFileSettingService.execute(dto);

		sessionManagerService.removeValue(request.getSession(), "healthInfoFileSettingForm");

		return getView(DashboardView.HEALTH_INFO_FILE_SETTING_COMPLETE);
	}

}
