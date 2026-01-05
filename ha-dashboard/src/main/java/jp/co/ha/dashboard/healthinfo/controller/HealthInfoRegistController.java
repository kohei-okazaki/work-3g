package jp.co.ha.dashboard.healthinfo.controller;

import static jp.co.ha.business.exception.DashboardErrorCode.*;
import static jp.co.ha.common.db.SelectOption.SortType.*;
import static jp.co.ha.common.exception.CommonErrorCode.*;
import static jp.co.ha.dashboard.view.DashboardView.*;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.api.healthinfoapp.response.HealthInfoRegistApiResponse;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.dto.HealthInfoDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.service.annotation.HealthInfoDownloadCsv;
import jp.co.ha.business.healthInfo.service.annotation.HealthInfoDownloadExcel;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvDownloadModel;
import jp.co.ha.business.io.file.excel.model.HealthInfoExcelComponent;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.ExcelConfig.ExcelConfigBuilder;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoForm;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoService;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康管理_健康情報登録画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("healthinfo")
public class HealthInfoRegistController implements BaseWizardController<HealthInfoForm> {

    /** 健康情報サービス */
    @Autowired
    private HealthInfoService healthInfoService;
    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** Excelダウンロードサービス */
    @Autowired
    @HealthInfoDownloadExcel
    private ExcelDownloadService<HealthInfoExcelComponent> excelDownloadService;
    /** CSVダウンロードサービス */
    @Autowired
    @HealthInfoDownloadCsv
    private CsvDownloadService<HealthInfoCsvDownloadModel> csvDownloadService;
    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** 健康情報ファイル設定 */
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

    @Override
    @GetMapping("/input")
    public String input(Model model, HttpServletRequest request) throws BaseException {
        return getView(model, HEALTH_INFO_INPUT);
    }

    @Override
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm")
    public String confirm(Model model, @Valid HealthInfoForm form, BindingResult result,
            HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            // バリエーションエラーの場合
            return getView(model, HEALTH_INFO_INPUT);
        }

        // sessionに健康情報Form情報を保持
        sessionComponent.setValue(request.getSession(), "healthInfoForm", form);

        return getView(model, HEALTH_INFO_CONFIRM);
    }

    @Override
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, HealthInfoForm form, HttpServletRequest request)
            throws BaseException {

        // sessionより健康情報Form情報を取得
        HealthInfoForm healthInfoForm = sessionComponent
                .getValue(request.getSession(), "healthInfoForm", HealthInfoForm.class)
                .orElseThrow(() -> new BusinessException(ILLEGAL_ACCESS_ERROR,
                        "不正リクエストエラーです"));

        HealthInfoDto dto = new HealthInfoDto();
        BeanUtil.copy(healthInfoForm, dto);

        // セッションからユーザIDを取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        boolean isFirstReg = healthInfoService.isFirstReg(seqUserId);
        model.addAttribute("isFirstReg", isFirstReg);

        if (!isFirstReg) {
            // 初回登録でない場合
            SelectOption selectOption = new SelectOptionBuilder()
                    .orderBy("SEQ_HEALTH_INFO_ID", DESC).limit(1).build();
            HealthInfo lastHealthInfo = healthInfoSearchService
                    .findBySeqUserId(seqUserId, selectOption).get(0);
            healthInfoService.addModel(model, dto, lastHealthInfo);
        }

        // 健康情報登録処理を行う
        HealthInfoRegistApiResponse apiResponse = healthInfoService.regist(dto,
                seqUserId);

        if (ResultType.SUCCESS == apiResponse.getResultType()) {
            // 健康情報の登録に成功した場合
            healthInfoService.sendHealthInfoMail(apiResponse);
        }

        // レスポンス情報をformに設定
        BeanUtil.copy(apiResponse.getHealthInfo(), healthInfoForm);
        // レスポンスを設定
        model.addAttribute("healthInfo", healthInfoForm);

        // CSVダウンロード時の健康情報IDの取得のため改めてsessionに設定
        sessionComponent.setValue(request.getSession(), "healthInfoForm",
                healthInfoForm);

        return getView(model, HEALTH_INFO_COMPLETE);
    }

    /**
     * 健康情報Excelをダウンロードする
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return {@linkplain ModelAndView}
     * @throws BaseException
     *     基底例外
     */
    @GetMapping("/exceldownload")
    public ModelAndView excelDownload(HttpServletRequest request) throws BaseException {

        // sessionよりユーザIDと健康情報Form情報を取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();
        HealthInfoForm healthInfoForm = sessionComponent
                .getValue(request.getSession(), "healthInfoForm", HealthInfoForm.class)
                .orElseThrow(() -> new BusinessException(ILLEGAL_ACCESS_ERROR,
                        "不正リクエストエラーです"));

        List<HealthInfo> healthInfoList = healthInfoSearchService
                .findByHealthInfoIdAndSeqUserId(healthInfoForm.getSeqHealthInfoId(),
                        seqUserId);
        if (CollectionUtil.isEmpty(healthInfoList)) {
            // レコードが見つからなかった場合
            throw new BusinessException(ILLEGAL_ACCESS_ERROR, "session情報が不正です");
        }
        HealthInfo entity = CollectionUtil.getFirst(healthInfoList);
        HealthInfoExcelComponent component = new HealthInfoExcelComponent();
        component.setHealthInfo(entity);

        return new ModelAndView(
                excelDownloadService.download(component, getExcelConfig(seqUserId)));
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
    @GetMapping("/csvdownload")
    public void csvDownload(HttpServletRequest request, HttpServletResponse response)
            throws BaseException {

        // sessionよりユーザIDと健康情報Form情報を取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();
        HealthInfoForm healthInfoForm = sessionComponent
                .getValue(request.getSession(), "healthInfoForm", HealthInfoForm.class)
                .orElseThrow(() -> new BusinessException(ILLEGAL_ACCESS_ERROR,
                        "不正リクエストエラーです"));

        List<HealthInfo> healthInfoList = healthInfoSearchService
                .findByHealthInfoIdAndSeqUserId(healthInfoForm.getSeqHealthInfoId(),
                        seqUserId);
        if (CollectionUtil.isEmpty(healthInfoList)) {
            // レコードが見つからなかった場合
            throw new BusinessException(ILLEGAL_ACCESS_ERROR, "session情報が不正です");
        }

        // 健康情報ファイル設定情報 取得
        HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService
                .findById(seqUserId).get();
        // CSV設定情報取得
        CsvConfig conf = healthInfoService.getCsvConfig(fileSetting);

        response.setContentType(
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset="
                        + conf.getCharset().getValue());
        response.setHeader("Content-Disposition",
                "attachment; filename=" + conf.getFileName());

        try {
            csvDownloadService.download(response.getWriter(), conf,
                    healthInfoService.toModelList(healthInfoList));
        } catch (IOException e) {
            throw new SystemException(FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました", e);
        }
    }

    /**
     * Excel設定情報を取得
     *
     * @param seqUserId
     *     ユーザID
     * @return Excel設定情報
     * @throws BaseException
     *     基底例外
     */
    private ExcelConfig getExcelConfig(Long seqUserId) throws BaseException {

        // 健康情報ファイル設定情報 取得
        HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService
                .findById(seqUserId).get();

        return new ExcelConfigBuilder(null)
                .hasHeader(fileSetting.getHeaderFlag())
                .hasFooter(fileSetting.getHeaderFlag())
                .useMask(fileSetting.getHeaderFlag())
                .build();
    }

}
