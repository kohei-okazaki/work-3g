package jp.co.ha.dashboard.healthinfo.controller;

import java.io.File;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.business.aws.AwsS3Component;
import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.healthInfo.HealthInfoGraphModel;
import jp.co.ha.business.healthInfo.service.HealthInfoGraphService;
import jp.co.ha.business.healthInfo.service.annotation.ReferenceDownloadCsv;
import jp.co.ha.business.healthInfo.service.annotation.ReferenceDownloadExcel;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelComponent;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.system.SystemConfig;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoReferenceForm;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoReferService;
import jp.co.ha.dashboard.healthinfo.validate.HealthInfoReferenceValidator;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.HealthInfoFileSetting;
import jp.co.ha.web.controller.BaseWebController;

/**
 * 健康管理_健康情報照会画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("healthinforeference")
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
    /** SessionComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** 健康情報ファイル設定検索サービス */
    @Autowired
    private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;
    /** System設定情報 */
    @Autowired
    private SystemConfig systemConfig;
    /** 健康情報グラフ作成サービス */
    @Autowired
    private HealthInfoGraphService healthInfoGraphService;
    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;

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
     * 照会前画面
     *
     * @param model
     *     Model
     * @return 照会前画面
     */
    @GetMapping(value = "/index")
    public String index(Model model) {
        // 検索処理実行フラグを設定
        model.addAttribute("isRefered", false);
        return getView(DashboardView.HEALTH_INFO_REFFERNCE);
    }

    /**
     * 照会後画面
     *
     * @param request
     *     HttpServletRequest
     * @param model
     *     Model
     * @param form
     *     検索情報フォーム
     * @param result
     *     BindingResult
     * @return 照会後画面
     * @throws BaseException
     *     基底例外
     */
    @PostMapping(value = "/index")
    public String reference(HttpServletRequest request, Model model,
            @Valid HealthInfoReferenceForm form, BindingResult result)
            throws BaseException {

        if (result.hasErrors()) {
            return getView(DashboardView.HEALTH_INFO_REFFERNCE);
        }

        String userId = sessionComponent
                .getValue(request.getSession(), "userId", String.class).get();

        HealthInfoReferenceDto dto = new HealthInfoReferenceDto();
        BeanUtil.copy(form, dto, (src, dest) -> {
            HealthInfoReferenceForm srcForm = (HealthInfoReferenceForm) src;
            HealthInfoReferenceDto destDto = (HealthInfoReferenceDto) dest;
            if (StringUtil.hasValue(srcForm.getSeqHealthInfoId())) {
                destDto.setSeqHealthInfoId(Integer.valueOf(srcForm.getSeqHealthInfoId()));
            }
        });

        List<HealthInfoReferenceDto> resultList = service.getHealthInfoResponseList(dto,
                userId);

        // 検索処理実行フラグを設定
        model.addAttribute("isRefered", true);
        // 検索情報を設定
        model.addAttribute("form", dto);
        // 検索結果有無を設定
        model.addAttribute("hasResult", !CollectionUtil.isEmpty(resultList));
        // ログイン中ユーザの健康情報リストを設定
        model.addAttribute("resultList", resultList);

        healthInfoGraphService.putGraph(model, () -> {

            HealthInfoGraphModel graphModel = new HealthInfoGraphModel();
            resultList.stream().forEach(e -> {
                graphModel.addHealthInfoRegDate(e.getHealthInfoRegDate());
                graphModel.addWeight(e.getWeight());
                graphModel.addBmi(e.getBmi());
                graphModel.addStandardWeight(e.getStandardWeight());
            });

            return graphModel;
        });

        model.addAttribute("systemConfig", systemConfig);

        // sessionに検索条件を設定
        sessionComponent.setValue(request.getSession(), "healthInfoReferenceDto", dto);

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
    @GetMapping(value = "/exceldownload")
    public ModelAndView excelDownload(HttpServletRequest request) throws BaseException {

        String userId = sessionComponent
                .getValue(request.getSession(), "userId", String.class).get();

        // sessionにある前画面の検索条件で再度検索する
        HealthInfoReferenceDto referDto = sessionComponent
                .getValue(request.getSession(), "healthInfoReferenceDto",
                        HealthInfoReferenceDto.class)
                .orElseThrow(() -> new SystemException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
        List<HealthInfoReferenceDto> resultList = service
                .getHealthInfoResponseList(referDto, userId);

        ReferenceExcelComponent component = new ReferenceExcelComponent();
        component.setUserId(userId);
        component.setResultList(resultList);
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
    @GetMapping(value = "/csvdownload")
    public void csvDownload(HttpServletRequest request, HttpServletResponse response)
            throws BaseException {

        String userId = sessionComponent
                .getValue(request.getSession(), "userId", String.class).get();
        // sessionにある前画面の検索条件で再度検索する
        HealthInfoReferenceDto referDto = sessionComponent
                .getValue(request.getSession(), "healthInfoReferenceDto",
                        HealthInfoReferenceDto.class)
                .orElseThrow(() -> new SystemException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "session情報が不正です"));
        List<HealthInfoReferenceDto> resultList = service
                .getHealthInfoResponseList(referDto, userId);

        // 健康情報ファイル設定情報 取得
        HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService
                .findById(userId).get();
        // CSV設定情報取得
        CsvConfig conf = service.getCsvConfig(fileSetting);

        response.setContentType(
                MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset="
                        + conf.getCharset().getValue());
        response.setHeader("Content-Disposition",
                "attachment; filename=" + conf.getFileName());

        try {
            // ResponseにCSV情報を書き出す
            csvDownloadService.download(response.getWriter(), conf,
                    service.toModelList(userId, resultList));
        } catch (IOException e) {
            throw new SystemException(CommonErrorCode.FILE_WRITE_ERROR,
                    "ファイルの出力処理に失敗しました", e);
        }

        // S3にCSVファイルをアップロードする
        // 一時ダウンロードファイル
        File file = FileUtil.getFile(conf.getOutputPath()
                + FileSeparator.SYSTEM.getValue() + conf.getFileName());
        awsS3Component.putFile("healthinfo-refer-file/" + userId + "/" + file.getName(),
                file);
    }

}
