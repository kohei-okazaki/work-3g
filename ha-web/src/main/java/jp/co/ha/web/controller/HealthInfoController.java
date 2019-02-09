package jp.co.ha.web.controller;

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
import jp.co.ha.business.exception.HealthInfoException;
import jp.co.ha.business.exception.WebErrorCode;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.common.exception.AppIOException;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionManageService;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.form.HealthInfoForm;
import jp.co.ha.web.service.HealthInfoService;
import jp.co.ha.web.service.annotation.HealthInfoDownloadCsv;
import jp.co.ha.web.service.annotation.HealthInfoDownloadExcel;
import jp.co.ha.web.view.ManageWebView;

/**
 * 健康管理_健康情報登録画面コントローラ
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
	@GetMapping(value = "/input.html")
	public String input(Model model, HttpServletRequest request) throws BaseException {
		return getView(ManageWebView.HEALTH_INFO_INPUT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PostMapping(value = "/confirm.html")
	public String confirm(Model model, @Valid HealthInfoForm form, BindingResult result, HttpServletRequest request) throws BaseException {

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
	public String complete(Model model, HealthInfoForm form, HttpServletRequest request) throws BaseException {

		// セッションからユーザIDを取得
		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();

		boolean isFirstReg = healthInfoService.isFirstReg(userId);
		model.addAttribute("isFirstReg", isFirstReg);

		if (!isFirstReg) {
			// 初回登録でない場合
			HealthInfo lastHealthInfo = healthInfoSearchService.findLastByUserId(userId);
			healthInfoService.addModel(model, form, lastHealthInfo);
		}

		// 健康情報登録処理を行う
		HealthInfoRegistResponse apiResponse = healthInfoService.regist(form, userId);

		// レスポンス情報をformに設定
		BeanUtil.copy(apiResponse, form);
		// レスポンスを設定
		model.addAttribute("healthInfo", form);

		return getView(ManageWebView.HEALTH_INFO_COMPLETE);
	}

	/**
	 * 健康情報Excelをダウンロードする
	 *
	 * @param userId
	 *     ユーザID
	 * @param form
	 *     健康情報画面Form
	 * @return ModelAndView
	 * @throws BaseException
	 *     基底例外
	 */
	@GetMapping(value = "/excelDownload.html")
	public ModelAndView excelDownload(HttpServletRequest request, HealthInfoForm form) throws BaseException {

		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();
		Integer requestHealthInfoId = form.getHealthInfoId();
		List<HealthInfo> healthInfoList = healthInfoSearchService.findByHealthInfoIdAndUserId(requestHealthInfoId, userId);
		if (CollectionUtil.isEmpty(healthInfoList)) {
			// レコードが見つからなかった場合
			throw new HealthInfoException(WebErrorCode.REQUEST_INFO_ERROR, "不正リクエストエラーが起きました");
		}
		HealthInfo entity = CollectionUtil.getFirst(healthInfoList);

		return new ModelAndView(excelDownloadService.execute(entity));
	}

	/**
	 * 健康情報CSVをダウンロードする
	 *
	 * @param request
	 *     HttpServletRequest
	 * @param response
	 *     HttpServletResponse
	 * @param form
	 *     健康情報画面Form
	 * @throws BaseException
	 *     基底例外
	 */
	@GetMapping(value = "/csvDownload.html")
	public void csvDownload(HttpServletRequest request, HttpServletResponse response, HealthInfoForm form) throws BaseException {

		String userId = sessionService.getValue(request.getSession(), "userId", String.class).get();
		List<HealthInfo> healthInfoList = healthInfoSearchService.findByHealthInfoIdAndUserId(form.getHealthInfoId(), userId);
		if (CollectionUtil.isEmpty(healthInfoList)) {
			// レコードが見つからなかった場合
			throw new HealthInfoException(WebErrorCode.REQUEST_INFO_ERROR, "不正リクエストエラーが起きました");
		}

		// CSV出力モデルリストに変換する
		List<HealthInfoCsvDownloadModel> modelList = healthInfoService.toModelList(healthInfoList);

		// CSV設定情報取得
		HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService.findByUserId(userId);
		CsvConfig conf = healthInfoService.getCsvConfig(fileSetting);
		response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=" + conf.getCharset().toString().toLowerCase());
		response.setHeader("Content-Disposition", "attachment; filename=" + conf.getFileName());

		try {
			csvDownloadService.execute(response.getWriter(), conf, modelList);
		} catch (IOException e) {
			throw new AppIOException(CommonErrorCode.FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました");
		}
	}

}
