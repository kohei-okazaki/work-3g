package jp.co.ha.dashboard.inquiry.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.inquiry.form.InquiryForm;

/**
 * 健康管理_問い合わせ画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("inquiry")
public class InquiryController implements BaseWebController {

    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;

    /**
     * Formを返す
     * 
     * @param request
     *     HttpServletRequest
     * @return 問い合わせForm
     * @throws BusinessException
     *     年齢の算出に失敗したとき
     */
    @ModelAttribute("inquiryForm")
    public InquiryForm setUpForm(HttpServletRequest request)
            throws BusinessException {

        InquiryForm form = new InquiryForm();

        return form;
    }
}
