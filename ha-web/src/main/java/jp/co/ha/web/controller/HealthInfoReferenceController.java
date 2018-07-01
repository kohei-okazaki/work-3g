package jp.co.ha.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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

import jp.co.ha.api.response.HealthInfoReferenceResponse;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.BaseWebController;
import jp.co.ha.web.form.HealthInfoReferenceForm;
import jp.co.ha.web.service.HealthInfoReferenceService;
import jp.co.ha.web.service.annotation.ReferenceDownloadCsv;
import jp.co.ha.web.service.annotation.ReferenceDownloadExcel;
import jp.co.ha.web.validator.HealthInfoReferenceValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報照会画面コントローラクラス<br>
 *
 */
@Controller
public class HealthInfoReferenceController implements BaseWebController {

	/** 結果照会画面サービス */
	@Autowired
	private HealthInfoReferenceService service;

	/** 結果照会Excelダウンロードサービス */
	@Autowired
	@ReferenceDownloadExcel
	private ExcelDownloadService<List<HealthInfoReferenceResponse>> excelDownloadService;
	/** 結果照会CSVダウンロードサービス */
	@Autowired
	@ReferenceDownloadCsv
	private CsvDownloadService csvDownloadService;
	/** セッション管理サービス */
	@Autowired
	private SessionManageService sessionService;

	/**
	 * Validateを設定<br>
	 *
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder(value = "healthInfoReferenceForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new HealthInfoReferenceValidator());
	}

	/**
	 * Formを返す<br>
	 *
	 * @return
	 */
	@ModelAttribute
	public HealthInfoReferenceForm setUpForm() {
		HealthInfoReferenceForm resultSearchForm = new HealthInfoReferenceForm();
		resultSearchForm.setRegDateSelectFlag(StringUtil.FALSE_FLAG);
		return resultSearchForm;
	}

	/**
	 * 検索照会画面<br>
	 *
	 * @param model
	 *            Model
	 * @return
	 */
	@GetMapping(value = "/healthInfo-reference.html")
	public String resultReference(Model model) {
		return getView(ManageWebView.HEALTH_INFO_REFFERNCE);
	}

	/**
	 * 検索結果画面を表示<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            Model
	 * @param userId
	 *            ユーザID
	 * @param form
	 *            検索情報フォーム
	 * @param result
	 *            BindingResult
	 * @return
	 */
	@PostMapping(value = "/healthInfo-reference.html")
	public String showSearchResult(HttpServletRequest request, Model model, @SessionAttribute String userId, @Valid HealthInfoReferenceForm form,
			BindingResult result) {

		if (result.hasErrors()) {
			return getView(ManageWebView.HEALTH_INFO_REFFERNCE);
		}

		List<HealthInfoReferenceResponse> resultList = service.getHealthInfoResponseList(form, userId);

		// 検索情報を設定
		model.addAttribute("form", form);
		// 検索結果有無を設定
		model.addAttribute("hasResult", true);
		// ログイン中のユーザの全レコードを検索する
		model.addAttribute("resultList", resultList);

		// sessionに検索結果リストを設定
		sessionService.setValue(request.getSession(), "resultList", resultList);

		return getView(ManageWebView.HEALTH_INFO_REFFERNCE);
	}

	/**
	 * Excelダウンロードを実行<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param resultList
	 *            List<HealthInfoReferenceResponse>
	 * @return
	 * @throws HealthInfoException
	 */
	@GetMapping(value = "/healthInfo-reference-excelDownload.html")
	public ModelAndView excelDownload(HttpServletRequest request
			, @SessionAttribute @Nullable List<HealthInfoReferenceResponse> resultList) throws HealthInfoException {

		if (BeanUtil.isNull(resultList) || resultList.isEmpty()) {
			// レコードが見つからなかった場合
			throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "不正リクエストエラーが起きました");
		}
		ModelAndView model = new ModelAndView(excelDownloadService.execute(resultList));

		return model;
	}

	/**
	 * CSVダウンロードを実行<br>
	 *
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	@GetMapping(value = "/healthInfo-reference-csvDownload")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) {
		csvDownloadService.execute(request, response);
	}
}
