package jp.co.ha.dashboard.healthinfo.controller;

import static jp.co.ha.business.exception.DashboardErrorCode.*;
import static jp.co.ha.common.exception.CommonErrorCode.*;
import static jp.co.ha.dashboard.view.DashboardView.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.ha.business.db.crud.read.HealthInfoFileSettingSearchService;
import jp.co.ha.business.dto.HealthInfoReferenceDto;
import jp.co.ha.business.healthInfo.HealthInfoGraphModel;
import jp.co.ha.business.healthInfo.service.HealthInfoGraphService;
import jp.co.ha.business.healthInfo.service.annotation.ReferenceDownloadCsv;
import jp.co.ha.business.healthInfo.service.annotation.ReferenceDownloadExcel;
import jp.co.ha.business.io.file.csv.model.ReferenceCsvDownloadModel;
import jp.co.ha.business.io.file.excel.model.ReferenceExcelComponent;
import jp.co.ha.common.aws.AwsS3Component;
import jp.co.ha.common.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.CsvConfig;
import jp.co.ha.common.io.file.csv.service.CsvDownloadService;
import jp.co.ha.common.io.file.excel.ExcelConfig;
import jp.co.ha.common.io.file.excel.ExcelConfig.ExcelConfigBuilder;
import jp.co.ha.common.io.file.excel.service.ExcelDownloadService;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.system.SystemProperties;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.FileUtil;
import jp.co.ha.common.util.FileUtil.FileSeparator;
import jp.co.ha.common.util.PagingViewFactory;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoReferenceForm;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoReferService;
import jp.co.ha.dashboard.healthinfo.validate.HealthInfoReferenceValidator;
import jp.co.ha.db.entity.HealthInfoFileSetting;

/**
 * 健康管理_健康情報照会画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("healthinforeference")
public class HealthInfoReferenceController implements BaseWebController {

    /** 健康情報照会画面サービス */
    @Autowired
    private HealthInfoReferService service;
    /** Excelダウンロードサービス */
    @Autowired
    @ReferenceDownloadExcel
    private ExcelDownloadService<ReferenceExcelComponent> excelDownloadService;
    /** 健康情報CSVアップロードサービス */
    @Autowired
    @ReferenceDownloadCsv
    private CsvDownloadService<ReferenceCsvDownloadModel> csvDownloadService;
    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** 健康情報ファイル設定検索サービス */
    @Autowired
    private HealthInfoFileSettingSearchService healthInfoFileSettingSearchService;
    /** システム情報設定 */
    @Autowired
    private SystemProperties systemConfig;
    /** 健康情報グラフ作成サービス */
    @Autowired
    private HealthInfoGraphService healthInfoGraphService;
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component s3;

    /**
     * Validateを設定
     *
     * @param binder
     *     {@linkplain WebDataBinder}
     */
    @InitBinder("healthInfoReferenceForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new HealthInfoReferenceValidator());
    }

    /**
     * Formを返す
     *
     * @return Form
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
     *     {@linkplain Model}
     * @return 照会前画面
     */
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("isRefered", false);
        return getView(model, HEALTH_INFO_REFERNCE);
    }

    /**
     * 照会処理を行う
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @param model
     *     {@linkplain Model}
     * @param form
     *     {@linkplain HealthInfoReferenceForm}
     * @param result
     *     {@linkplain BindingResult}
     * @param page
     *     ページ数
     * @return 照会後画面
     * @throws BaseException
     *     基底例外
     */
    @GetMapping("/refer")
    public String refer(HttpServletRequest request, Model model,
            @Valid HealthInfoReferenceForm form, BindingResult result,
            @RequestParam(name = "page", defaultValue = "0", required = false) String page)
            throws BaseException {

        if (result.hasErrors()) {
            return getView(model, HEALTH_INFO_REFERNCE);
        }

        // ページング情報を取得(1ページあたりの表示件数は設定ファイルより取得)
        Pageable pageable = PagingViewFactory.getPageable(page, systemConfig.getPaging());

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        HealthInfoReferenceDto dto = new HealthInfoReferenceDto();
        BeanUtil.copy(form, dto, (src, dest) -> {
            HealthInfoReferenceForm srcForm = (HealthInfoReferenceForm) src;
            HealthInfoReferenceDto destDto = (HealthInfoReferenceDto) dest;
            if (StringUtil.hasValue(srcForm.getSeqHealthInfoId())) {
                destDto.setSeqHealthInfoId(Long.valueOf(srcForm.getSeqHealthInfoId()));
            }
        });

        List<HealthInfoReferenceDto> resultList = service.getHealthInfoResponseList(dto,
                seqUserId, pageable);

        // 検索処理実行フラグを設定
        model.addAttribute("isRefered", true);
        // 検索情報を設定
        model.addAttribute("form", dto);
        // 検索結果有無を設定
        model.addAttribute("hasResult", !CollectionUtil.isEmpty(resultList));
        // ログイン中ユーザの健康情報リストを設定
        model.addAttribute("resultList", resultList);
        // ページング情報を設定
        model.addAttribute("paging", PagingViewFactory.getPageView(pageable,
                "/healthinforeference/refer?seqHealthInfoId=" + form.getSeqHealthInfoId()
                        + "&healthInfoRegDateSelectFlag="
                        + form.getHealthInfoRegDateSelectFlag()
                        + "&fromHealthInfoRegDate=" + form.getFromHealthInfoRegDate()
                        + "&toHealthInfoRegDate=" + form.getToHealthInfoRegDate()
                        + "&page",
                service.getCount(dto, seqUserId)));

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

        // sessionに検索条件を設定
        sessionComponent.setValue(request.getSession(), "healthInfoReferenceDto", dto);

        return getView(model, HEALTH_INFO_REFERNCE);
    }

    /**
     * Excelダウンロードを実行
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return {@linkplain ModelAndView}
     * @throws BaseException
     *     基底例外
     */
    @GetMapping("/exceldownload")
    public ModelAndView excelDownload(HttpServletRequest request) throws BaseException {

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        // sessionにある前画面の検索条件で再度検索する
        HealthInfoReferenceDto referDto = sessionComponent
                .getValue(request.getSession(), "healthInfoReferenceDto",
                        HealthInfoReferenceDto.class)
                .orElseThrow(() -> new SystemException(ILLEGAL_ACCESS_ERROR,
                        "session情報が不正です"));
        List<HealthInfoReferenceDto> resultList = service
                .getHealthInfoResponseList(referDto, seqUserId, null);

        ReferenceExcelComponent component = new ReferenceExcelComponent();
        component.setResultList(resultList);

        return new ModelAndView(
                excelDownloadService.download(component, getExcelConfig(seqUserId)));
    }

    /**
     * CSVダウンロードを実行
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @param response
     *     {@linkplain HttpServletResponse}
     * @throws BaseException
     *     基底例外
     */
    @GetMapping("/csvdownload")
    public void csvDownload(HttpServletRequest request, HttpServletResponse response)
            throws BaseException {

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();
        // sessionにある前画面の検索条件で再度検索する
        HealthInfoReferenceDto referDto = sessionComponent
                .getValue(request.getSession(), "healthInfoReferenceDto",
                        HealthInfoReferenceDto.class)
                .orElseThrow(() -> new SystemException(ILLEGAL_ACCESS_ERROR,
                        "session情報が不正です"));
        List<HealthInfoReferenceDto> resultList = service
                .getHealthInfoResponseList(referDto, seqUserId, null);

        // 健康情報ファイル設定情報 取得
        HealthInfoFileSetting fileSetting = healthInfoFileSettingSearchService
                .findById(seqUserId).get();
        // CSV設定情報取得
        CsvConfig conf = service.getCsvConfig(fileSetting);

        response.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset="
                + conf.getCharset().getValue());
        response.setHeader("Content-Disposition",
                "attachment; filename=" + conf.getFileName());

        try {
            // ResponseにCSV情報を書き出す
            csvDownloadService.download(response.getWriter(), conf,
                    service.toModelList(seqUserId, resultList));
        } catch (IOException e) {
            throw new SystemException(FILE_WRITE_ERROR, "ファイルの出力処理に失敗しました", e);
        }

        // S3にCSVファイルをアップロードする
        // 一時ダウンロードファイル
        File file = FileUtil.getFile(conf.getOutputPath()
                + FileSeparator.SYSTEM.getValue() + conf.getFileName());
        String s3Key = new StringJoiner(StringUtil.THRASH)
                .add(AwsS3Key.HEALTHINFO_FILE_REFERENCE.getValue())
                .add(seqUserId.toString())
                .add(file.getName())
                .toString();
        s3.putFile(s3Key, file);
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
                .hasFooter(fileSetting.getFooterFlag())
                .useMask(fileSetting.getMaskFlag())
                .build();
    }

}
