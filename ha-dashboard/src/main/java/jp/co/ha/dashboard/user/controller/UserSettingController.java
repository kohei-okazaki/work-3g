package jp.co.ha.dashboard.user.controller;

import java.util.Optional;

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
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.dashboard.user.form.UserSettingForm;
import jp.co.ha.dashboard.user.service.UserSettingService;
import jp.co.ha.dashboard.user.service.impl.UserSettingServiceImpl;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.composite.CompositeUser;

/**
 * 健康管理_ユーザ設定コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("accountsetting")
public class UserSettingController
        implements BaseWizardController<UserSettingForm> {

    /** セッションキー：フォーム */
    private static final String SESSION_KEY_FORM = "userSettingForm";

    /** {@linkplain UserSettingServiceImpl} */
    @Autowired
    private UserSettingService userSettingService;
    /** {@linkplain UserSearchServiceImpl} */
    @Autowired
    private UserSearchService userSearchService;
    /** {@linkplain SessionComponent} */
    @Autowired
    private SessionComponent sessionComponent;

    /**
     * Formを返す
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return UserSettingForm
     */
    @ModelAttribute("userSettingForm")
    public UserSettingForm setUpForm(HttpServletRequest request) {

        // セッションからユーザIDを取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        Optional<CompositeUser> entity = userSearchService
                .findCompositUserById(seqUserId);

        UserSettingForm form = new UserSettingForm();
        BeanUtil.copy(entity.get(), form, (src, dest) -> {
            CompositeUser srcAccount = (CompositeUser) src;
            UserSettingForm destForm = (UserSettingForm) dest;
            destForm.setPasswordExpire(DateTimeUtil
                    .toString(srcAccount.getPasswordExpire(), DateFormatType.YYYYMMDD));
            destForm.setDeleteFlag(srcAccount.isDeleteFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setHeaderFlag(srcAccount.isHeaderFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setFooterFlag(srcAccount.isFooterFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setMaskFlag(srcAccount.isMaskFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setEnclosureCharFlag(
                    srcAccount.isEnclosureCharFlag() ? CommonFlag.TRUE.getValue()
                            : CommonFlag.FALSE.getValue());
        });

        return form;
    }

    @Override
    @GetMapping("/input")
    public String input(Model model, HttpServletRequest request) throws BaseException {
        return getView(model, DashboardView.ACCOUNT_SETTING_INPUT);
    }

    @Override
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm")
    public String confirm(Model model, @Valid UserSettingForm form,
            BindingResult result, HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            return getView(model, DashboardView.ACCOUNT_SETTING_INPUT);
        }

        // sessionにユーザ設定form情報を保持
        sessionComponent.setValue(request.getSession(), SESSION_KEY_FORM, form);

        return getView(model, DashboardView.ACCOUNT_SETTING_CONFIRM);
    }

    @Override
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, UserSettingForm form,
            HttpServletRequest request) throws BaseException {

        // sessionよりユーザ設定form情報を取得
        UserSettingForm userSettingForm = sessionComponent
                .getValue(request.getSession(), SESSION_KEY_FORM,
                        UserSettingForm.class)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

        UserDto dto = new UserDto();
        BeanUtil.copy(userSettingForm, dto);
        dto.setDeleteFlag(CommonFlag.of(userSettingForm.getDeleteFlag()).get());
        dto.setHeaderFlag(CommonFlag.of(userSettingForm.getHeaderFlag()).get());
        dto.setFooterFlag(CommonFlag.of(userSettingForm.getFooterFlag()).get());
        dto.setMaskFlag(CommonFlag.of(userSettingForm.getMaskFlag()).get());
        dto.setEnclosureCharFlag(
                CommonFlag.of(userSettingForm.getEnclosureCharFlag()).get());

        // form情報から更新処理を行う
        userSettingService.execute(dto);

        sessionComponent.removeValue(request.getSession(), SESSION_KEY_FORM);

        return getView(model, DashboardView.ACCOUNT_SETTING_COMPLETE);
    }

}
