package jp.co.ha.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;
import jp.co.ha.web.service.annotation.HealthInfoCsv;
import jp.co.ha.web.service.annotation.HealthInfoExcel;
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
	private ExcelDownloadService<HealthInfo> excelDownloadService;
	/** 健康情報CSVダウンロードサービス */
	@Autowired
	@HealthInfoCsv
	private CsvDownloadService csvDownloadService;
	/** セッション管理サービス */
	@Autowired
	private SessionManageService sessionService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder("healthInfoForm")
	public void initBinder(WebDataBinder binder) {
//		binder.setValidator(new HealthInfoValidator());
	}

	/**
	 * Formを返す<br>
	 * @return
	 */
	@ModelAttribute
	public HealthInfoForm setUpForm() {
		return new HealthInfoForm();
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

		// セッションからユーザIDを取得
		String userId = sessionService.getValue(request, "userId", String.class);
		HealthInfoRegistRequest apiRequest = healthInfoService.setUpApiRequest(form, userId);

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
	 *
	 * @param userId
	 *            ユーザID
	 * @return
	 */
	@GetMapping(value = "/healthInfo-excelDownload.html")
	public ModelAndView excelDownload(@SessionAttribute String userId) {

		HealthInfo entity = healthInfoSearchService.findLastByUserId(userId);

		ModelAndView model = new ModelAndView(excelDownloadService.execute(entity));
		return model;
	}

	/**
	 * 健康情報CSVをダウンロードする<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return
	 */
	@GetMapping(value = "/healthInfo-csvDownload")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) {
		csvDownloadService.execute(request, response);
	}

}
