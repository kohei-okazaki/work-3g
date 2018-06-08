package jp.co.ha.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.web.BaseWebController;
import jp.co.ha.web.form.ResultSearchForm;
import jp.co.ha.web.service.ResultReferenceService;
import jp.co.ha.web.service.annotation.ReferenceCsv;
import jp.co.ha.web.service.annotation.ReferenceExcel;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報結果照会画面コントローラクラス<br>
 *
 */
@Controller
public class ResultReferenceController implements BaseWebController {

	/** 結果照会画面サービス */
	@Autowired
	private ResultReferenceService service;

	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 結果照会Excelダウンロードサービス */
	@Autowired
	@ReferenceExcel
	private ExcelDownloadService<List<HealthInfo>> fileDownloadService;
	/** 結果照会CSVダウンロードサービス */
	@Autowired
	@ReferenceCsv
	private CsvDownloadService csvDownloadService;

	/**
	 * Formを返す<br>
	 *
	 * @return
	 */
	@ModelAttribute
	public ResultSearchForm setUpForm() {
		return new ResultSearchForm();
	}

	/**
	 * 検索照会画面<br>
	 *
	 * @param model
	 *            Model
	 * @return
	 */
	@GetMapping(value = "/result-reference.html")
	public String resultReference(Model model) {
		return getView(ManageWebView.RESULT_REFFERNCE);
	}

	/**
	 * 検索結果画面を表示<br>
	 *
	 * @param model
	 *            Model
	 * @param userId
	 *            ユーザID
	 * @param form
	 *            検索情報フォーム
	 * @return
	 */
	@PostMapping(value = "/result-reference.html")
	public String showSearchResult(Model model, @SessionAttribute String userId, @Valid ResultSearchForm form,
			BindingResult result) {

		if (result.hasErrors()) {
			return getView(ManageWebView.RESULT_REFFERNCE);
		}
		service.setUpForm(form);

		List<HealthInfoRegistResponse> resultList = service.getHealthInfoResponseList(form, userId);

		// 検索情報を設定
		model.addAttribute("form", form);
		// 検索結果有無を設定
		model.addAttribute("hasResult", true);
		// ログイン中のユーザの全レコードを検索する
		model.addAttribute("resultList", resultList);

		return getView(ManageWebView.RESULT_REFFERNCE);
	}

	/**
	 * Excelダウンロードを実行<br>
	 *
	 * @param userId
	 *            ユーザID
	 * @return
	 */
	@GetMapping(value = "/result-reference-excelDownload.html")
	public ModelAndView excelDownload(@SessionAttribute String userId) {

		List<HealthInfo> healthInfoList = healthInfoSearchService.findByUserId(userId);
		ModelAndView model = new ModelAndView(fileDownloadService.execute(healthInfoList));

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
	@GetMapping(value = "/result-reference-csvDownload")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) {
		csvDownloadService.execute(request, response);
	}
}
