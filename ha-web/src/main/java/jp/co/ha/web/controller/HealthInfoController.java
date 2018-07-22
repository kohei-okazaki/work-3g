package jp.co.ha.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.api.request.HealthInfoRegistRequest;
import jp.co.ha.api.response.HealthInfoRegistResponse;
import jp.co.ha.api.service.HealthInfoRegistService;
import jp.co.ha.business.find.HealthInfoFileSettingSearchService;
import jp.co.ha.business.find.HealthInfoSearchService;
import jp.co.ha.business.parameter.ParamConst;
import jp.co.ha.common.entity.HealthInfo;
import jp.co.ha.common.entity.HealthInfoFileSetting;
import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseAppException;
import jp.co.ha.common.exception.ErrorCode;
import jp.co.ha.common.exception.HealthInfoException;
import jp.co.ha.common.file.csv.CsvConfig;
import jp.co.ha.common.file.csv.service.CsvDownloadService;
import jp.co.ha.common.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.BaseWizardController;
import jp.co.ha.web.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;
import jp.co.ha.web.service.annotation.HealthInfoDownloadCsv;
import jp.co.ha.web.service.annotation.HealthInfoDownloadExcel;
import jp.co.ha.web.validator.HealthInfoValidator;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報入力画面コントローラ<br>
 *
 */
@Controller
@RequestMapping("healthInfo")
public class HealthInfoController implements BaseWizardController<HealthInfoForm> {

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
	@HealthInfoDownloadExcel
	private ExcelDownloadService<HealthInfo> excelDownloadService;
	/** 健康情報CSVダウンロードサービス */
	@Autowired
	@HealthInfoDownloadCsv
	private CsvDownloadService<HealthInfoCsvDownloadModel> csvDownloadService;
	/** セッション管理サービス */
	@Autowired
	private SessionManageService sessionService;
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@InitBinder("healthInfoForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new HealthInfoValidator());
	}

	/**
	 * Formを返す<br>
	 *
	 * @return
	 */
	@ModelAttribute("healthInfoForm")
	public HealthInfoForm setUpForm() {
		return new HealthInfoForm();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/input.html")
	public String input(Model model, HttpServletRequest request) throws BaseAppException {
		return getView(ManageWebView.HEALTH_INFO_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/confirm.html")
	public String confirm(Model model, @Valid HealthInfoForm form, BindingResult result) throws BaseAppException {

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
	@PostMapping(value = "/complete.html")
	public String complete(Model model, HealthInfoForm form, HttpServletRequest request) throws BaseAppException {

		// セッションからユーザIDを取得
		String userId = sessionService.getValue(request.getSession(), "userId", String.class);
		if (BeanUtil.isNull(userId)) {
			throw new HealthInfoException(ErrorCode.ILLEGAL_ACCESS_ERROR, "session内のユーザIDが不正です");
		}
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

		// レスポンス情報をformに設定
		BeanUtil.copy(apiResponse, form);
		// レスポンスを設定
		model.addAttribute("healthInfo", form);

		return getView(ManageWebView.HEALTH_INFO_COMPLETE);
	}

	/**
	 * 健康情報Excelをダウンロードする<br>
	 *
	 * @param userId
	 *     ユーザID
	 * @param form
	 *     HealthInfoForm
	 * @return
	 * @throws HealthInfoException
	 *     健康情報例外
	 */
	@GetMapping(value = "/excelDownload.html")
	public ModelAndView excelDownload(@SessionAttribute @Nullable String userId, HealthInfoForm form) throws BaseAppException {

		BigDecimal requestHealthInfoId = form.getHealthInfoId();
		boolean hasRecord = healthInfoService.hasRecord(healthInfoSearchService.findByUserId(userId), requestHealthInfoId);

		if (!hasRecord) {
			// レコードが見つからなかった場合
			throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "不正リクエストエラーが起きました");
		}

		HealthInfo entity = healthInfoSearchService.findByHealthInfoId(requestHealthInfoId);
		ModelAndView model = new ModelAndView(excelDownloadService.execute(entity));

		return model;
	}

	/**
	 * 健康情報CSVをダウンロードする<br>
	 *
	 * @param request
	 *     HttpServletRequest
	 * @param response
	 *     HttpServletResponse
	 * @param form
	 *     健康情報フォーム
	 * @throws HealthInfoException
	 *     健康情報例外
	 */
	@GetMapping(value = "/csvDownload.html")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response, HealthInfoForm form) throws BaseAppException {

		String userId = sessionService.getValue(request.getSession(), "userId", String.class);
		if (BeanUtil.isNull(userId)) {
			throw new HealthInfoException(ErrorCode.ILLEGAL_ACCESS_ERROR, "session内のユーザIDが不正です");
		}

		boolean hasRecord = healthInfoService.hasRecord(healthInfoSearchService.findByUserId(userId), form.getHealthInfoId());
		if (!hasRecord) {
			// レコードが見つからなかった場合
			throw new HealthInfoException(ErrorCode.REQUEST_INFO_ERROR, "不正リクエストエラーが起きました");
		}

		// CSV出力モデルリストに変換する
		List<HealthInfoCsvDownloadModel> modelList = healthInfoService.toModelList(healthInfoSearchService.findLastByUserId(userId));

		// CSV設定情報取得
		HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService.findByUserId(userId);
		CsvConfig conf = csvDownloadService.getCsvConfig(ParamConst.CSV_FILE_NAME_HEALTH_INFO.getValue(), fileSetting);
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + conf.getCharset().toString().toLowerCase());
		response.setHeader("Content-Disposition", "attachment; filename=" + conf.getFileName());

		try {
			csvDownloadService.execute(response.getWriter(), conf, modelList);
		} catch (AppIOException e) {
			throw new AppIOException(ErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		} catch (IOException e) {
			throw new AppIOException(ErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		}
	}

}
