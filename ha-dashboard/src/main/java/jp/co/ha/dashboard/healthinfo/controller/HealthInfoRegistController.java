package jp.co.ha.dashboard.healthinfo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.business.api.response.HealthInfoRegistResponse;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.CsrfToken;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelComponent;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoForm;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoService;
import jp.co.ha.dashboard.healthinfo.service.annotation.HealthInfoDownloadCsv;
import jp.co.ha.dashboard.healthinfo.service.annotation.HealthInfoDownloadExcel;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.controller.BaseWizardController;

/**
 * 健康管理_健康情報登録画面コントローラ
 *
 * @since 1.0
 */
@Controller
@RequestMapping("healthinfo")
public class HealthInfoRegistController implements BaseWizardController<HealthInfoForm> {

	/** 健康情報画面サービス */
	@Autowired
	private HealthInfoService healthInfoService;
	/** 健康情報検索サービス */
	@Autowired
	private HealthInfoSearchService healthInfoSearchService;
	/** 健康情報Excelダウンロードサービス */
	@Autowired
	@HealthInfoDownloadExcel
	private ExcelDownloadService<HealthInfoExcelComponent> excelDownloadService;
	/** 健康情報CSVダウンロードサービス */
	@Autowired
	@HealthInfoDownloadCsv
	private CsvDownloadService<HealthInfoCsvDownloadModel> csvDownloadService;
	/** セッション管理サービス */
	@Autowired
	private SessionManageService sessionManagerService;
	/** 健康情報ファイル設定検索サービス */
	@Autowired
	private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;

	/**
	 * Formを返す
	 *
	 * @return HealthInfoForm
	 */
	@ModelAttribute("healthInfoForm")
	public HealthInfoForm setUpForm() {
		return new HealthInfoForm();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@GetMapping(value = "/input")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(DashboardView.HEALTH_INFO_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CsrfToken(factocy = true)
	@PostMapping(value = "/confirm")
	public String confirm(Model model, @Valid HealthInfoForm form, BindingResult result, HttpServletRequest request)
			throws BaseException {

		if (result.hasErrors()) {
			// バリエーションエラーの場合
			return getView(DashboardView.HEALTH_INFO_INPUT);
		}

		// sessionに健康情報Form情報を保持
		sessionManagerService.setValue(request.getSession(), "healthInfoForm", form);

		return getView(DashboardView.HEALTH_INFO_CONFIRM);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@CsrfToken(check = true)
	@PostMapping(value = "/complete")
	public String complete(Model model, HealthInfoForm form, HttpServletRequest request) throws BaseException {

		// sessionより健康情報Form情報を取得
		HealthInfoForm healthInfoForm = sessionManagerService
				.getValue(request.getSession(), "healthInfoForm", HealthInfoForm.class)
				.orElseThrow(() -> new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

		HealthInfoDto dto = new HealthInfoDto();
		BeanUtil.copy(healthInfoForm, dto);

		// セッションからユーザIDを取得
		String userId = sessionManagerService.getValue(request.getSession(), "userId", String.class).get();

		boolean isFirstReg = healthInfoService.isFirstReg(userId);
		model.addAttribute("isFirstReg", isFirstReg);

		if (!isFirstReg) {
			// 初回登録でない場合
			SelectOption selectOption = new SelectOptionBuilder().orderBy("HEALTH_INFO_ID", SortType.DESC).limit(1)
					.build();
			HealthInfo lastHealthInfo = healthInfoSearchService.findByUserId(userId, selectOption).get(0);
			healthInfoService.addModel(model, dto, lastHealthInfo);
		}

		// 健康情報登録処理を行う
		HealthInfoRegistResponse apiResponse = healthInfoService.regist(dto, userId);

		// レスポンス情報をformに設定
		BeanUtil.copy(apiResponse, healthInfoForm);
		// レスポンスを設定
		model.addAttribute("healthInfo", healthInfoForm);

		sessionManagerService.setValue(request.getSession(), "healthInfoForm", healthInfoForm);

		return getView(DashboardView.HEALTH_INFO_COMPLETE);
	}

	/**
	 * 健康情報Excelをダウンロードする
	 *
	 * @param request
	 *     HttpServletRequest
	 * @return ModelAndView
	 * @throws BaseException
	 *     基底例外
	 */
	@GetMapping(value = "/exceldownload")
	public ModelAndView excelDownload(HttpServletRequest request) throws BaseException {

		// sessionよりユーザIDと健康情報Form情報を取得
		String userId = sessionManagerService.getValue(request.getSession(), "userId", String.class).get();
		HealthInfoForm healthInfoForm = sessionManagerService
				.getValue(request.getSession(), "healthInfoForm", HealthInfoForm.class)
				.orElseThrow(() -> new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

		List<HealthInfo> healthInfoList = healthInfoSearchService
				.findByHealthInfoIdAndUserId(healthInfoForm.getHealthInfoId(), userId);
		if (CollectionUtil.isEmpty(healthInfoList)) {
			// レコードが見つからなかった場合
			throw new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}
		HealthInfo entity = CollectionUtil.getFirst(healthInfoList);
		HealthInfoExcelComponent component = new HealthInfoExcelComponent();
		component.setHealthInfo(entity);
		return new ModelAndView(excelDownloadService.download(component));
	}

	/**
	 * 健康情報CSVをダウンロードする
	 *
	 * @param request
	 *     HttpServletRequest
	 * @param response
	 *     HttpServletResponse
	 * @throws BaseException
	 *     基底例外
	 */
	@GetMapping(value = "/csvdownload")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response) throws BaseException {

		// sessionよりユーザIDと健康情報Form情報を取得
		String userId = sessionManagerService.getValue(request.getSession(), "userId", String.class).get();
		HealthInfoForm healthInfoForm = sessionManagerService
				.getValue(request.getSession(), "healthInfoForm", HealthInfoForm.class)
				.orElseThrow(() -> new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

		List<HealthInfo> healthInfoList = healthInfoSearchService
				.findByHealthInfoIdAndUserId(healthInfoForm.getHealthInfoId(), userId);
		if (CollectionUtil.isEmpty(healthInfoList)) {
			// レコードが見つからなかった場合
			throw new BusinessException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です");
		}

		// CSV出力モデルリストに変換する
		List<HealthInfoCsvDownloadModel> modelList = healthInfoService.toModelList(healthInfoList);

		// 健康情報ファイル設定情報 取得
		HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService.findByUserId(userId).get();
		CsvConfig conf = healthInfoService.getCsvConfig(fileSetting);
		response.setContentType(
				MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + conf.getCharset().getValue());
		response.setHeader("Content-Disposition", "attachment; filename=" + conf.getFileName());

		try {
			csvDownloadService.download(response.getWriter(), conf, modelList);
		} catch (IOException e) {
			throw new SystemException(CommonErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました", e);
		} catch (BaseException e) {
			throw e;
		}
	}

}
