package jp.co.ha.web.controller;

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

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.file.csv.service.CsvUploadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.BaseWebController;
import jp.co.ha.web.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.web.form.HealthInfoFileForm;
import jp.co.ha.web.service.HealthInfoFileRegistService;
import jp.co.ha.web.service.annotation.HealthInfoUploadCsv;
import jp.co.ha.web.validator.HealthInfoFileInputValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康情報ファイル登録画面コントローラ<br>
 *
 */
@Controller
public class HealthInfoFileRegistController implements BaseWebController {

	/** 健康情報CSVアップロードサービス */
	@Autowired
	@HealthInfoUploadCsv
	private CsvUploadService<HealthInfoCsvUploadModel> csvUploadService;
	/** 健康情報ファイル登録画面サービス */
	@Autowired
	private HealthInfoFileRegistService fileService;
	/** 健康情報登録サービス */
	@Autowired
	private HealthInfoRegistService healthInfoRegistService;
	/** session管理サービス */
	@Autowired
	private SessionManageService sessionManageService;

	/**
	 * フォームを返す<br>
	 *
	 * @return healthInfoFileForm 健康情報ファイル入力画面フォーム
	 */
	@ModelAttribute
	public HealthInfoFileForm setUpForm() {
		HealthInfoFileForm healthInfoFileForm = new HealthInfoFileForm();
		return healthInfoFileForm;
	}

	/**
	 * {@inheritDoc}
	 */
	@InitBinder("healthInfoFileForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new HealthInfoFileInputValidator());
	}

	/**
	 * {@inheritDoc}
	 */
	@GetMapping(value = "/healthInfoFile-input.html")
	public String input(Model model, HttpServletRequest request) throws BaseAppException {
		return ManageWebView.HEALTH_INFO_FILE_INPUT.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@PostMapping(value = "/healthInfoFile-confirm.html")
	public String confirm(Model model, @Valid HealthInfoFileForm form, BindingResult result, HttpServletRequest request) throws BaseAppException {

		if (result.hasErrors()) {
			// validationエラーの場合
			return ManageWebView.HEALTH_INFO_FILE_INPUT.getName();
		}

		List<HealthInfoCsvUploadModel> modelList = csvUploadService.execute(form.getMultipartFile());
		fileService.formatCheck(modelList);
		sessionManageService.setValue(request.getSession(), "modelList", modelList);
		model.addAttribute("count", modelList.size());

		return ManageWebView.HEALTH_INFO_FILE_CONFIRM.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@PostMapping(value = "/healthInfoFile-complete.html")
	public String complete(Model model, HealthInfoFileForm form, HttpServletRequest request) throws BaseAppException {
		List<HealthInfoCsvUploadModel> modelList = sessionManageService.getValue(request.getSession(), "modelList", List.class);
		if (BeanUtil.isNull(modelList)) {
			throw new HealthInfoException(ErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}
		List<HealthInfoRegistRequest> reqList = fileService.toRequestList(modelList);
		for (HealthInfoRegistRequest apiRequest : reqList) {
			healthInfoRegistService.checkRequest(apiRequest);
			healthInfoRegistService.execute(apiRequest);
		}
		return ManageWebView.HEALTH_INFO_FILE_COMPLETE.getName();
	}

}
