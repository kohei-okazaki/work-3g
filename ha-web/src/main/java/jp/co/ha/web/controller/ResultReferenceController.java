package jp.co.ha.web.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.util.DateFormatDefine;
import jp.co.ha.common.util.DateUtil;
import jp.co.ha.common.web.BaseWebController;
import jp.co.ha.web.form.ResultSearchForm;
import jp.co.ha.web.service.annotation.ReferenceCsv;
import jp.co.ha.web.service.annotation.ReferenceExcel;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報結果照会画面コントローラクラス<br>
 *
 */
@Controller
public class ResultReferenceController implements BaseWebController {

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
	 * 検索照会画面<br>
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/result-reference.html")
	public String resultReference(Model model) {
		return getView(ManageWebView.RESULT_REFFERNCE);
	}

	/**
	 * 結果照会画面
	 * @param model
	 * @param userId
	 * @param form
	 * @return
	 */
	@PostMapping(value = "/result-reference.html")
	public String resultReferenceDetail(Model model, @SessionAttribute String userId, ResultSearchForm form) {

		List<HealthInfo> entityList = healthInfoSearchService.findByUserId(userId);
		List<HealthInfoRegistResponse> resultList = new ArrayList<HealthInfoRegistResponse>();
		for (HealthInfo entity : entityList) {
			HealthInfoRegistResponse response = new HealthInfoRegistResponse();
			BeanUtils.copyProperties(entity, response);
			response.setRegDate(DateUtil.toString(entity.getRegDate(), DateFormatDefine.YYYYMMDD_HHMMSS));
			resultList.add(response);
		}

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
	 * @param userId
	 * @return
	 */
	@GetMapping(value = "/result-reference-excelDownload.html")
	public ModelAndView excelDownload(@SessionAttribute String userId) {

		List<HealthInfo> healthInfoList = healthInfoSearchService.findByUserId(userId);

		ModelAndView model = new ModelAndView(fileDownloadService.execute(healthInfoList));
		model.setViewName(getView(ManageWebView.RESULT_REFFERNCE));

		return model;
	}

	/**
	 * CSVダウンロードを実行<br>
	 * @param request
	 * @param response
	 * @throws ParseException
	 * @throws IOException
	 */
	@GetMapping(value = "/result-reference-csvDownload")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		csvDownloadService.execute(request, response);
	}
}
