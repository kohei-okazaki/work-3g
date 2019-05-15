package jp.co.ha.dashboard.controller;

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
import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.dashboard.form.HealthInfoReferenceForm;
import jp.co.ha.dashboard.service.HealthInfoReferService;
import jp.co.ha.dashboard.service.annotation.ReferenceDownloadCsv;
import jp.co.ha.dashboard.service.annotation.ReferenceDownloadExcel;
import jp.co.ha.dashboard.validator.HealthInfoReferenceValidator;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_健康情報照会画面コントローラ
 *
 */
@Controller
@RequestMapping("healthInfoReference")
public class HealthInfoReferenceController implements BaseWebController {

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
	@GetMapping(value = "/index")
	public String reference() {
		return getView(DashboardView.HEALTH_INFO_REFFERNCE);
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
	@PostMapping(value = "/index")
	public String reference(HttpServletRequest request, Model model, @Valid HealthInfoReferenceForm form,
			BindingResult result) throws BaseException {

		if (result.hasErrors()) {
			return getView(DashboardView.HEALTH_INFO_REFFERNCE);
		}

		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();

		HealthInfoReferenceDto dto = new HealthInfoReferenceDto();
		BeanUtil.copy(form, dto);
		dto.setHealthInfoId(Integer.valueOf(form.getHealthInfoId()));
		List<HealthInfoReferenceDto> resultList = service.getHealthInfoResponseList(dto, userId);

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
		for (HealthInfoReferenceDto referenceResult : resultList) {
			healthInfoRegDateList.add(referenceResult.getHealthInfoRegDate());
			weightList.add(referenceResult.getWeight());
			bmiList.add(referenceResult.getBmi());
			standardWeightList.add(referenceResult.getStandardWeight());
		}
		model.addAttribute("label", healthInfoRegDateList);
		model.addAttribute("weight", weightList);
		model.addAttribute("bmi", bmiList);
		model.addAttribute("standardWeight", standardWeightList);

		// sessionに検索結果リストを設定
		sessionService.setValue(request.getSession(), "resultList", resultList);

		return getView(DashboardView.HEALTH_INFO_REFFERNCE);
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
	@GetMapping(value = "/excelDownload")
	public ModelAndView excelDownload(HttpServletRequest request) throws BaseException {

		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();
		List<HealthInfoReferenceDto> referList = sessionService
				.getValue(request.getSession(), "resultList", List.class)
				.orElseThrow(() -> new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));

		if (CollectionUtil.isEmpty(referList)) {
			throw new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}
		ReferenceExcelComponent component = new ReferenceExcelComponent();
		component.setUserId(userId);
		component.setResultList(referList);
		return new ModelAndView(excelDownloadService.download(component));
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
	@GetMapping(value = "/csvDownload")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) throws BaseException {

		HttpSession session = request.getSession();
		List<HealthInfoReferenceDto> resultList = sessionService.getValue(session, "resultList", List.class)
				.orElseThrow(() -> new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
		String userId = sessionService.getValue(session, "userId", String.class).get();

		if (CollectionUtil.isEmpty(resultList)) {
			throw new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}

		// CSV設定情報取得
		HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService.findByUserId(userId);
		CsvConfig conf = service.getCsvConfig(fileSetting);

		response.setContentType(
				MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + conf.getCharset().getValue());
		response.setHeader("Content-Disposition", "attachment; filename=" + conf.getFileName());

		try {
			csvDownloadService.download(response.getWriter(), conf, service.toModelList(userId, resultList));
		} catch (IOException e) {
			throw new SystemException(CommonErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました", e);
		} catch (BaseException e) {
			throw e;
		}
	}

}
