package jp.co.ha.dashboard.controller;

import java.util.List;

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

import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.CsrfToken;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.service.CsvUploadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.dashboard.form.HealthInfoFileForm;
import jp.co.ha.dashboard.service.HealthInfoFileRegistService;
import jp.co.ha.dashboard.service.annotation.HealthInfoUploadCsv;
import jp.co.ha.dashboard.validator.HealthInfoFileInputValidator;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWizardController;

/**
 * 健康情報ファイル登録画面コントローラ
 *
 */
@Controller
@RequestMapping("healthInfoFile")
public class HealthInfoFileRegistController implements BaseWizardController<HealthInfoFileForm> {

	/** 健康情報CSVアップロードサービス */
	@Autowired
	@HealthInfoUploadCsv
	private CsvUploadService<HealthInfoCsvUploadModel> csvUploadService;
	/** 健康情報ファイル登録画面サービス */
	@Autowired
	private HealthInfoFileRegistService fileService;
	/** session管理サービス */
	@Autowired
	private SessionManageService sessionManageService;

	/**
	 * フォームを返す
	 *
	 * @return 健康情報ファイル入力画面フォーム
	 */
	@ModelAttribute("healthInfoFileForm")
	public HealthInfoFileForm setUpForm() {
		return new HealthInfoFileForm();
	}

	/**
	 * validatorを設定
	 *
	 * @param binder
	 *     WebDataBinder
	 */
	@InitBinder("healthInfoFileForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new HealthInfoFileInputValidator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/input")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(DashboardView.HEALTH_INFO_FILE_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CsrfToken(factocy = true)
	@PostMapping(value = "/confirm")
	public String confirm(Model model, @Valid HealthInfoFileForm form, BindingResult result, HttpServletRequest request)
			throws BaseException {

		if (result.hasErrors()) {
			// validationエラーの場合
			return getView(DashboardView.HEALTH_INFO_FILE_INPUT);
		}

		String userId = sessionManageService.getValue(request.getSession(), "userId", String.class).get();

		List<HealthInfoCsvUploadModel> modelList = csvUploadService.upload(form.getMultipartFile());
		fileService.formatCheck(modelList, userId);
		sessionManageService.setValue(request.getSession(), "modelList", modelList);
		model.addAttribute("modelList", modelList);
		model.addAttribute("count", modelList.size());

		return getView(DashboardView.HEALTH_INFO_FILE_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CsrfToken(check = true)
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/complete")
	public String complete(Model model, HealthInfoFileForm form, HttpServletRequest request) throws BaseException {

		List<HealthInfoCsvUploadModel> modelList = sessionManageService
				.getValue(request.getSession(), "modelList", List.class)
				.orElseThrow(() -> new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
		if (CollectionUtil.isEmpty(modelList)) {
			throw new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}

		String userId = sessionManageService.getValue(request.getSession(), "userId", String.class).get();

		fileService.regist(modelList, userId);
		sessionManageService.removeValue(request.getSession(), "modelList");
		return getView(DashboardView.HEALTH_INFO_FILE_COMPLETE);
	}

}
