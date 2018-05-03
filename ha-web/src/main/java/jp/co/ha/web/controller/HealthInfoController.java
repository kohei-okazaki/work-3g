package jp.co.ha.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.api.RequestType;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;
import jp.co.ha.web.service.annotation.HealthInfoCsv;
import jp.co.ha.web.service.annotation.HealthInfoExcel;
import jp.co.ha.web.validator.HealthInfoValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報入力画面コントローラ<br>
 *
 */
@Controller
public class HealthInfoController implements BaseWizardController<HealthInfoForm, HealthInfoException> {

	/** 健康情報画面サービス */
	@Autowired
	private HealthInfoService healthInfoService;
	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 健康情報登録サービス */
	@Autowired
	private HealthInfoRegistService healthInfoRegistService;
	/** 健康情報Excelダウンロードサービス */
	@Autowired
	@HealthInfoExcel
	private ExcelDownloadService<HealthInfoForm> excelDownloadService;
	/** 健康情報CSVダウンロードサービス */
	@Autowired
	@HealthInfoCsv
	private CsvDownloadService csvDownloadService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder("HealthInfoForm")
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
			// バリエーションエラーの場合
			List<FieldError> errorList = result.getFieldErrors();
			FieldError error = errorList.get(0);
			model.addAttribute("errorMessage", error.getRejectedValue());
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

		HealthInfoRegistRequest apiRequest = new HealthInfoRegistRequest();
		// フォーム情報をリクエストクラスにコピー
		BeanUtils.copyProperties(form, apiRequest);
		// セッションからユーザIDを取得
		String userId = (String) request.getSession().getAttribute("userId");
		apiRequest.setUserId(userId);
		// リクエストタイプ設定
		apiRequest.setRequestType(RequestType.HEALTH_INFO_REGIST);

		boolean isFirstReg = healthInfoService.isFirstReg(userId);
		model.addAttribute("isFirstReg", isFirstReg);

		if (!isFirstReg) {
			// 初回登録でない場合
			HealthInfo lastHealthInfo = healthInfoSearchService.findLastByUserId(userId);
			model.addAttribute("beforeWeight", lastHealthInfo.getWeight());
			model.addAttribute("diffWeight", healthInfoService.getDiffWeight(form, lastHealthInfo));
			model.addAttribute("resultMessage", healthInfoService.getDiffMessage(form, lastHealthInfo));

		}

		healthInfoRegistService.checkRequest(apiRequest);
		// 健康情報登録処理を行う
		HealthInfoRegistResponse apiResponse = healthInfoRegistService.execute(apiRequest);

		// レスポンスを設定
		model.addAttribute("healthInfo", apiResponse);

		return getView(ManageWebView.HEALTH_INFO_COMPLETE);
	}

	/**
	 * 健康情報Excelをダウンロードする<br>
	 * @param form
	 * @return
	 */
	@GetMapping(value = "/healthInfo-excelDownload.html")
	public ModelAndView excelDownload(HealthInfoForm form) {
		return new ModelAndView(this.excelDownloadService.execute(form));
	}

	/**
	 * 健康情報CSVをダウンロードする<br>
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/healthInfo-csvDownload")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) {
		csvDownloadService.execute(request, response);
	}

}
