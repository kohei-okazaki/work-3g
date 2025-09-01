package jp.co.ha.dashboard.inquiry.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.component.InquiryComponent;
import jp.co.ha.business.dto.InquiryDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.dashboard.inquiry.form.InquiryForm;
import jp.co.ha.dashboard.view.DashboardView;

/**
 * 健康管理_問い合わせ画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("inquiry")
public class InquiryController implements BaseWizardController<InquiryForm> {

    /** セッションキー：form */
    private static final String SESSION_KEY_FORM = "inquiryForm";

    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** 問い合わせComponent */
    @Autowired
    private InquiryComponent inquiryComponent;

    /**
     * Formを返す
     * 
     * @param request
     *     HttpServletRequest
     * @return 問い合わせForm
     */
    @ModelAttribute("inquiryForm")
    public InquiryForm setUpForm(HttpServletRequest request) {
        InquiryForm form = new InquiryForm();
        return form;
    }

    @Override
    @GetMapping("/input")
    public String input(Model model, HttpServletRequest request) throws BaseException {

        // 問い合わせ種別リストを取得
        // TODO キャッシュ化すること
        model.addAttribute("typeList", inquiryComponent.getInquiryTypeMtList());

        return getView(model, DashboardView.INQUIRY_INPUT);
    }

    @Override
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm")
    public String confirm(Model model, @Valid InquiryForm form, BindingResult result,
            HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            // バリエーションエラーの場合
            return getView(model, DashboardView.INQUIRY_INPUT);
        }

        // sessionに問い合わせForm情報を保持
        sessionComponent.setValue(request.getSession(), SESSION_KEY_FORM, form);

        return getView(model, DashboardView.INQUIRY_CONFIRM);
    }

    @Override
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, InquiryForm form, HttpServletRequest request)
            throws BaseException {

        // セッションからユーザIDを取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        // セッションから問い合わせForm情報を取得
        InquiryForm inquiryForm = sessionComponent
                .getValue(request.getSession(), SESSION_KEY_FORM, InquiryForm.class)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

        // 問い合わせ管理情報を登録する
        InquiryDto dto = new InquiryDto();
        BeanUtil.copy(inquiryForm, dto);
        dto.setSeqUserId(seqUserId);
        inquiryComponent.regist(dto);

        // 問い合わせ完了メールを送信する
        inquiryComponent.sendMail();

        sessionComponent.removeValue(request.getSession(), SESSION_KEY_FORM);

        return getView(model, DashboardView.INQUIRY_COMPLETE);
    }
}
