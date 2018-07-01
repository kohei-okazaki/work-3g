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
import org.springframework.web.multipart.MultipartFile;

import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.file.csv.service.CsvUploadService;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.file.csv.model.HealthInfoUploadModel;
import jp.co.ha.web.form.HealthInfoFileForm;
import jp.co.ha.web.service.annotation.HealthInfoUploadCsv;
import jp.co.ha.web.validator.HealthInfoFileInputValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康情報ファイル画面コントローラ<br>
 *
 */
@Controller
public class HealthInfoFileInputController implements BaseWizardController<HealthInfoFileForm> {

	/** 健康情報CSVアップロードサービス */
	@Autowired
	@HealthInfoUploadCsv
	private CsvUploadService<HealthInfoUploadModel> csvUploadService;

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
	@Override
	@InitBinder("healthInfoFileForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new HealthInfoFileInputValidator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/healthInfo-fileInput.html")
	public String input(Model model, HttpServletRequest request) throws BaseAppException {
		return ManageWebView.HEALTH_INFO_FILE_INPUT.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/healthInfo-fileConfirm.html")
	public String confirm(Model model, @Valid HealthInfoFileForm form, BindingResult result) throws BaseAppException {

		if (result.hasErrors()) {
			// validationエラーの場合
			return ManageWebView.HEALTH_INFO_FILE_INPUT.getName();
		}
		MultipartFile file = form.getMultipartFile();
		csvUploadService.execute(file);
		return ManageWebView.HEALTH_INFO_FILE_CONFIRM.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/healthInfo-fileComplete.html")
	public String complete(Model model, HealthInfoFileForm form, HttpServletRequest request) throws BaseAppException {
		return ManageWebView.HEALTH_INFO_FILE_COMPLETE.getName();
	}

}
