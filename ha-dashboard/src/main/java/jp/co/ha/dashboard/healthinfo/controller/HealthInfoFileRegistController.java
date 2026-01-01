package jp.co.ha.dashboard.healthinfo.controller;

import static jp.co.ha.business.exception.DashboardErrorCode.*;
import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;
import static jp.co.ha.dashboard.view.DashboardView.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.StringJoiner;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

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

import jp.co.ha.business.api.healthinfoapp.response.BaseAppApiResponse.ResultType;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.healthInfo.service.annotation.HealthInfoUploadCsv;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.io.file.csv.model.HealthInfoCsvUploadModel;
import jp.co.ha.business.io.file.csv.reader.HealthInfoCsvReader;
import jp.co.ha.common.aws.AwsS3Component;
import jp.co.ha.common.aws.AwsS3Component.AwsS3Key;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.csv.service.CsvUploadService;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.type.Charset;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.dashboard.healthinfo.form.HealthInfoFileForm;
import jp.co.ha.dashboard.healthinfo.service.HealthInfoFileRegistService;
import jp.co.ha.dashboard.healthinfo.validate.HealthInfoFileInputValidator;

/**
 * 健康情報一括登録画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("healthinfofile")
public class HealthInfoFileRegistController
        implements BaseWizardController<HealthInfoFileForm> {

    /** 健康情報登録ファイルPrefix */
    private static final String FILE_NAME_PREFIX = "healthinfo-file-regist/";

    /** 健康情報CSVアップロードサービス */
    @Autowired
    @HealthInfoUploadCsv
    private CsvUploadService<HealthInfoCsvUploadModel> csvUploadService;
    /** 健康情報ファイル登録サービス */
    @Autowired
    private HealthInfoFileRegistService fileService;
    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** AWS-S3 Component */
    @Autowired
    private AwsS3Component s3;

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
        return getView(model, HEALTH_INFO_FILE_INPUT);
    }

    @Override
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm")
    public String confirm(Model model, @Valid HealthInfoFileForm form,
            BindingResult result, HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            // validationエラーの場合
            return getView(model, HEALTH_INFO_FILE_INPUT);
        }

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        // Formの健康情報CSVからmodelリストを読み取る
        List<HealthInfoCsvUploadModel> modelList = new HealthInfoCsvReader()
                .readMultipartFile(form.getMultipartFile(), Charset.UTF_8);

        String fileName = DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                YYYYMMDDHHMMSS_NOSEP) + FileExtension.CSV;
        String s3Key = new StringJoiner(StringUtil.THRASH)
                .add(AwsS3Key.HEALTHINFO_FILE_REGIST.getValue())
                .add(seqUserId.toString())
                .add(fileName).toString();
        s3.putFile(s3Key, form.getMultipartFile());

        // フォーマットチェックを行う
        fileService.formatCheck(modelList, seqUserId);

        // アップロードファイル名を設定
        sessionComponent.setValue(request.getSession(), FILE_NAME_PREFIX + seqUserId,
                fileName);

        model.addAttribute("modelList", modelList);
        model.addAttribute("count", modelList.size());

        return getView(model, HEALTH_INFO_FILE_CONFIRM);
    }

    @Override
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, HealthInfoFileForm form,
            HttpServletRequest request) throws BaseException {

        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        String fileName = sessionComponent
                .getValue(request.getSession(), FILE_NAME_PREFIX + seqUserId,
                        String.class)
                .orElseThrow(() -> new BusinessException(
                        ILLEGAL_ACCESS_ERROR, "リクエスト情報が不正です セッションキー"
                                + FILE_NAME_PREFIX + seqUserId + "が存在しない"));

        List<HealthInfoCsvUploadModel> modelList = getModelList(seqUserId, fileName);

        if (CollectionUtil.isEmpty(modelList)) {
            throw new SystemException(ILLEGAL_ACCESS_ERROR,
                    "session情報が不正です");
        }

        ResultType result = fileService.regist(modelList, seqUserId);

        if (ResultType.SUCCESS == result) {
            sessionComponent.removeValue(request.getSession(),
                    FILE_NAME_PREFIX + seqUserId);
            s3.removeS3ObjectByKeys(FILE_NAME_PREFIX + seqUserId);
        }

        return getView(model, HEALTH_INFO_FILE_COMPLETE);
    }

    /**
     * 健康情報CSVモデルリストを返す
     * 
     * @param seqUserId
     *     ユーザID
     * @param fileName
     *     ファイル名
     * @return 健康情報CSVモデルリスト
     * @throws BaseException
     *     JSON読み込みに失敗した場合
     */
    private List<HealthInfoCsvUploadModel> getModelList(Long seqUserId,
            String fileName) throws BaseException {

        // S3から健康情報CSVファイルを取得
        try (InputStream is = s3
                .getS3ObjectByKey(AwsS3Key.HEALTHINFO_FILE_REGIST.getValue() + seqUserId
                        + "/" + fileName)) {

            List<HealthInfoCsvUploadModel> modelList = new HealthInfoCsvReader()
                    .readInputStream(is, Charset.UTF_8);

            return modelList;

        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

}
