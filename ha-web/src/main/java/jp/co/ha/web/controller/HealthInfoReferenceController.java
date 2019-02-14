package jp.co.ha.web.controller;

import java.io.IOException;
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

import jp.co.ha.business.api.response.HealthInfoReferenceResponse;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SessionIllegalException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoReferenceForm;
import jp.co.ha.web.service.HealthInfoReferenceService;
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
		form.setHealthInfoRegDateSelectFlag(StringUtil.FALSE_FLAG);
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

		List<HealthInfoReferenceResponse> resultList = service.getHealthInfoResponseList(form, userId);

		// 検索情報を設定
		model.addAttribute("form", form);
		// 検索結果有無を設定
		model.addAttribute("hasResult", !CollectionUtil.isEmpty(resultList));
		// ログイン中のユーザの全レコードを検索する
		model.addAttribute("resultList", resultList);

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

		List<HealthInfoReferenceResponse> resultList = sessionService.getValue(request.getSession(), "resultList", List.class)
				.orElseThrow(() -> new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
		if (CollectionUtil.isEmpty(resultList)) {
			// レコードが見つからなかった場合
			throw new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}

		return new ModelAndView(excelDownloadService.execute(resultList));
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

		// sessionから検索結果リストとユーザIDを取得
		HttpSession session = request.getSession();
		List<HealthInfoReferenceResponse> resultList = sessionService.getValue(session, "resultList", List.class)
				.orElseThrow(() -> new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
		String userId = sessionService.getValue(session, "userId", String.class).get();
		if (CollectionUtil.isEmpty(resultList)) {
			throw new SessionIllegalException(WebErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}

		// CSV設定情報取得
		HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService.findByUserId(userId);
		CsvConfig conf = service.getCsvConfig(fileSetting);
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + conf.getCharset().toString().toLowerCase());
		response.setHeader("Content-Disposition", "attachment; filename=" + conf.getFileName());

		try {
			csvDownloadService.execute(response.getWriter(), conf, service.toModelList(userId, resultList));
		} catch (IOException e) {
			throw new AppIOException(CommonErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		}
	}

}
