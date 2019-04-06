package jp.co.ha.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.business.healthInfo.result.HealthInfoReferenceResult;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelComponent;
import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SessionIllegalException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoReferenceForm;
import jp.co.ha.web.service.HealthInfoReferService;
import jp.co.ha.web.service.annotation.ReferenceDownloadCsv;
import jp.co.ha.web.service.annotation.ReferenceDownloadExcel;
import jp.co.ha.web.validator.HealthInfoReferenceValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報照会画面コントローラ
 *
 */
@Controller
@RequestMapping("healthInfoReference")
public class HealthInfoReferController implements BaseWebController {

	/** 結果照会画面サービス */
	@Autowired
	private HealthInfoReferService service;
	/** 結果照会Excelダウンロードサービス */
	@Autowired
	@ReferenceDownloadExcel
	private ExcelDownloadService<ReferenceExcelComponent> excelDownloadService;
	/** 結果照会CSVダウンロードサービス */
	@Autowired
	@ReferenceDownloadCsv
	private CsvDownloadService<ReferenceCsvDownloadModel> csvDownloadService;
	/** セッション管理サービス */
	@Autowired
	private SessionManageService sessionService;
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;

	/**
	 * Validateを設定
	 *
	 * @param binder
	 *     WebDataBinder
	 */
	@InitBinder(value = "healthInfoReferenceForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new HealthInfoReferenceValidator());
	}

	/**
	 * Formを返す
	 *
	 * @return HealthInfoReferenceForm
	 */
	@ModelAttribute("healthInfoReferenceForm")
	public HealthInfoReferenceForm setUpForm() {
		HealthInfoReferenceForm form = new HealthInfoReferenceForm();
		form.setHealthInfoRegDateSelectFlag(CommonFlag.FALSE.getValue());
		return form;
	}

	/**
	 * 検索照会画面
	 *
	 * @return 検索照会画面
	 */
	@GetMapping(value = "/index.html")
	public String reference() {
		return getView(ManageWebView.HEALTH_INFO_REFFERNCE);
	}

	/**
	 * 検索結果画面を表示
	 *
	 * @param request
	 *     HttpServletRequest
	 * @param model
	 *     Model
	 * @param form
	 *     検索情報フォーム
	 * @param result
	 *     BindingResult
	 * @return 検索結果画面
	 * @throws BaseException
	 *     基底例外
	 */
	@PostMapping(value = "/index.html")
	public String reference(HttpServletRequest request, Model model
			, @Valid HealthInfoReferenceForm form, BindingResult result) throws BaseException {

		if (result.hasErrors()) {
			return getView(ManageWebView.HEALTH_INFO_REFFERNCE);
		}

		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();

		List<HealthInfoReferenceResult> resultList = service.getHealthInfoResponseList(form, userId);

		// 検索情報を設定
		model.addAttribute("form", form);
		// 検索結果有無を設定
		model.addAttribute("hasResult", !CollectionUtil.isEmpty(resultList));
		// ログイン中のユーザの全レコードを検索する
		model.addAttribute("resultList", resultList);

		List<String> healthInfoRegDateList = new ArrayList<>();
		List<BigDecimal> weightList = new ArrayList<>();
		List<BigDecimal> bmiList = new ArrayList<>();
		List<BigDecimal> standardWeightList = new ArrayList<>();
		for (HealthInfoReferenceResult response : resultList) {
			healthInfoRegDateList.add(response.getHealthInfoRegDate());
			weightList.add(response.getWeight());
			bmiList.add(response.getBmi());
			standardWeightList.add(response.getStandardWeight());
		}
		model.addAttribute("label", healthInfoRegDateList);
		model.addAttribute("weight", weightList);
		model.addAttribute("bmi", bmiList);
		model.addAttribute("standardWeight", standardWeightList);

		// sessionに検索結果リストを設定
		sessionService.setValue(request.getSession(), "resultList", resultList);

		return getView(ManageWebView.HEALTH_INFO_REFFERNCE);
	}

	/**
	 * Excelダウンロードを実行
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return ModelAndView
	 * @throws BaseException
	 *     基底例外
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/excelDownload.html")
	public ModelAndView excelDownload(HttpServletRequest request) throws BaseException {

		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();
		List<HealthInfoReferenceResult> resultList = sessionService.getValue(request.getSession(), "resultList", List.class)
				.orElseThrow(() -> new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));

		if (CollectionUtil.isEmpty(resultList)) {
			throw new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}
		ReferenceExcelComponent component = new ReferenceExcelComponent();
		component.setUserId(userId);
		component.setResultList(resultList);
		return new ModelAndView(excelDownloadService.execute(component));
	}

	/**
	 * CSVダウンロードを実行
	 *
	 * @param request
	 *     HttpServletRequest
	 * @param response
	 *     HttpServletResponse
	 * @throws BaseException
	 *     基底例外
	 */
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/csvDownload.html")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) throws BaseException {

		HttpSession session = request.getSession();
		List<HealthInfoReferenceResult> resultList = sessionService.getValue(session, "resultList", List.class)
				.orElseThrow(() -> new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
		String userId = sessionService.getValue(session, "userId", String.class).get();

		if (CollectionUtil.isEmpty(resultList)) {
			throw new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}

		// CSV設定情報取得
		HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService.findByUserId(userId);
		CsvConfig conf = service.getCsvConfig(fileSetting);

		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + conf.getCharset().getValue());
		response.setHeader("Content-Disposition", "attachment; filename=" + conf.getFileName());

		try {
			csvDownloadService.execute(response.getWriter(), conf, service.toModelList(userId, resultList));
		} catch (IOException e) {
			throw new AppIOException(CommonErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました", e);
		} catch (BaseException e) {
			throw e;
		}
	}

}