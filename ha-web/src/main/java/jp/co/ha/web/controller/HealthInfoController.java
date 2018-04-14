package jp.co.ha.web.controller;

import java.util.List;
import java.util.Objects;

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

import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.service.HealthInfoSearchService;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;
import jp.co.ha.web.validator.HealthInfoValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報入力画面コントローラ<br>
 *
 */
@Controller
public class HealthInfoController implements BaseWizardController<HealthInfoForm, HealthInfoException> {

	@Autowired
	private HealthInfoService healthInfoService;
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new HealthInfoValidator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/healthInfo-input.html")
	public String input(Model model, HttpServletRequest request) throws HealthInfoException {
		return getView(ManageWebView.HEALTH_INFO_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/healthInfo-confirm.html")
	public String confirm(Model model, @Valid HealthInfoForm form, BindingResult result) throws HealthInfoException {

		if (result.hasErrors()) {
			return getView(ManageWebView.HEALTH_INFO_INPUT);
		}

		// 入力情報を設定
		model.addAttribute("form", form);

		return getView(ManageWebView.HEALTH_INFO_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/healthInfo-complete.html")
	public String complete(Model model, HealthInfoForm form, HttpServletRequest request) throws HealthInfoException {

		String userId = (String) request.getSession().getAttribute("userId");
		if (Objects.isNull(userId)) {
			throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "リクエスト情報が不正です");
		}

		// ユーザIDから健康情報のリストを取得
		List<HealthInfo> healthInfoList = this.healthInfoSearchService.findHealthInfoByUserId(userId);

		return getView(ManageWebView.HEALTH_INFO_COMPLETE);
	}

}
