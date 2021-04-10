package jp.co.ha.dashboard.healthinfo.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.api.aws.AwsS3Component;
import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.healthInfo.service.annotation.HealthInfoUploadCsv;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.business.io.file.csv.reader.HealthInfoCsvReader;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.service.CsvUploadService;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoFileForm;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoFileRegistService;
import jp.co.ha.dashboard.healthinfo.validate.HealthInfoFileInputValidator;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWizardController;
import jp.co.ha.web.form.BaseRestApiResponse.ResultType;

/**
 * 健康情報一括登録画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("healthinfofile")
public class HealthInfoFileRegistController
        implements BaseWizardController<HealthInfoFileForm> {

    /** 健康情報CSVアップロードサービス */
    @Autowired
    @HealthInfoUploadCsv
    private CsvUploadService<HealthInfoCsvUploadModel> csvUploadService;
    /** 健康情報ファイル登録画面サービス */
    @Autowired
    private HealthInfoFileRegistService fileService;
    /** SessionComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** S3コンポーネント */
    @Autowired
    private AwsS3Component awsS3Component;

    /**
     * フォームを返す
     *
     * @return 健康情報ファイル入力画面フォーム
     */
    @ModelAttribute("healthInfoFileForm")
    public HealthInfoFileForm setUpForm() {
        return new HealthInfoFileForm();
    }

    /**
     * validatorを設定
     *
     * @param binder
     *     {@linkplain WebDataBinder}
     */
    @InitBinder("healthInfoFileForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new HealthInfoFileInputValidator());
    }

    @Override
    @GetMapping("/input")
    public String input(Model model, HttpServletRequest request) throws BaseException {
        return getView(model, DashboardView.HEALTH_INFO_FILE_INPUT);
    }

    @Override
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm")
    public String confirm(Model model, @Valid HealthInfoFileForm form,
            BindingResult result, HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            // validationエラーの場合
            return getView(model, DashboardView.HEALTH_INFO_FILE_INPUT);
        }

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        // Formの健康情報CSVからmodelリストを読み取る
        List<HealthInfoCsvUploadModel> modelList = new HealthInfoCsvReader()
                .readMultipartFile(form.getMultipartFile(), Charset.UTF_8);

        String fileName = DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                DateFormatType.YYYYMMDDHHMMSS_NOSEP) + ".csv";
        awsS3Component.putFile(
                AwsS3Key.HEALTHINFO_FILE_REGIST.getValue() + seqUserId + "/" + fileName,
                form.getMultipartFile());

        // フォーマットチェックを行う
        fileService.formatCheck(modelList, seqUserId);

        // アップロードファイル名を設定
        sessionComponent.setValue(request.getSession(),
                "healthinfo-file-regist/" + seqUserId, fileName);

        model.addAttribute("modelList", modelList);
        model.addAttribute("count", modelList.size());

        return getView(model, DashboardView.HEALTH_INFO_FILE_CONFIRM);
    }

    @Override
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, HealthInfoFileForm form,
            HttpServletRequest request) throws BaseException {

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        String fileName = sessionComponent
                .getValue(request.getSession(), "healthinfo-file-regist/" + seqUserId,
                        String.class)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "リクエスト情報が不正です セッションキー"
                                + "healthinfo-file-regist/" + seqUserId + "が存在しない"));

        // S3から健康情報CSVファイルを取得
        InputStream is = awsS3Component
                .getS3ObjectByKey(AwsS3Key.HEALTHINFO_FILE_REGIST.getValue() + seqUserId
                        + "/" + fileName);
        List<HealthInfoCsvUploadModel> modelList = new HealthInfoCsvReader()
                .readInputStream(is, Charset.UTF_8);

        if (CollectionUtil.isEmpty(modelList)) {
            throw new SystemException(DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                    "session情報が不正です");
        }

        ResultType result = fileService.regist(modelList, seqUserId);

        sessionComponent.removeValue(request.getSession(),
                "healthinfo-file-regist/" + seqUserId);

        if (ResultType.FAILURE == result) {
            // TODO DB登録成功時、S3から登録ファイルを削除
        }

        return getView(model, DashboardView.HEALTH_INFO_FILE_COMPLETE);
    }

}
