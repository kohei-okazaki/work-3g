package jp.co.ha.dashboard.user.controller;

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

import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.db.crud.read.impl.UserSearchServiceImpl;
import jp.co.ha.business.dto.UserDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.dashboard.user.form.UserRegistForm;
import jp.co.ha.dashboard.user.service.UserRegistService;
import jp.co.ha.dashboard.user.service.impl.UserRegistServiceImpl;
import jp.co.ha.dashboard.view.DashboardView;

/**
 * 健康管理_ユーザ登録画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("accountregist")
public class UserRegistController implements BaseWizardController<UserRegistForm> {

    /** {@linkplain UserRegistServiceImpl} */
    @Autowired
    private UserRegistService userRegistService;
    /** {@linkplain UserSearchServiceImpl} */
    @Autowired
    private UserSearchService userSearchService;
    /** {@linkplain SessionComponent} */
    @Autowired
    private SessionComponent sessionComponent;

    /**
     * Formを返す
     *
     * @return UserRegistForm
     */
    @ModelAttribute("userRegistForm")
    public UserRegistForm setUpForm() {
        return new UserRegistForm();
    }

    @Override
    @NonAuth
    @GetMapping("/input")
    public String input(Model model, HttpServletRequest request) throws BaseException {
        return getView(model, DashboardView.ACCOUNT_REGIST_INPUT);
    }

    @Override
    @NonAuth
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm")
    public String confirm(Model model, @Valid UserRegistForm form,
            BindingResult result, HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            return getView(model, DashboardView.ACCOUNT_REGIST_INPUT);
        }

        if (userSearchService.isExistByMailAddress(form.getMailAddress())) {
            model.addAttribute("errorMessage", "指定されたメールアドレスは既に登録されています");
            return getView(model, DashboardView.ACCOUNT_REGIST_INPUT);
        }

        // sessionにユーザ登録Form情報を保持
        sessionComponent.setValue(request.getSession(), "userRegistForm", form);

        return getView(model, DashboardView.ACCOUNT_REGIST_CONFIRM);
    }

    @Override
    @NonAuth
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, UserRegistForm form,
            HttpServletRequest request) throws BaseException {

        // sessionよりユーザ登録Form情報を取得
        UserRegistForm accountRegistForm = sessionComponent
                .getValue(request.getSession(), "userRegistForm",
                        UserRegistForm.class)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

        UserDto dto = new UserDto();
        BeanUtil.copy(accountRegistForm, dto);

        // Form情報から登録処理を行う
        userRegistService.regist(dto);

        sessionComponent.removeValue(request.getSession(), "userRegistForm");

        return getView(model, DashboardView.ACCOUNT_REGIST_COMPLETE);
    }

}
