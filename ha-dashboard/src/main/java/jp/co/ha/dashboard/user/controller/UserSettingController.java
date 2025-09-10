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

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.dto.UserDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.healthInfo.type.GenderType;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.type.CommonFlag;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.web.controller.BaseWizardController;
import jp.co.ha.dashboard.user.form.UserSettingForm;
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

    /** セッションキー：ユーザID */
    private static final String SESSION_KEY_SEQ_USER_ID = "seqUserId";
    /** セッションキー：フォーム */
    private static final String SESSION_KEY_FORM = "userSettingForm";

    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;
    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;

    /**
     * Formを返す
     *
     * @param request
     *     リクエスト情報
     * @return UserSettingForm
     */
    @ModelAttribute("userSettingForm")
    public UserSettingForm setUpForm(HttpServletRequest request) {

        // セッションからユーザIDを取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), SESSION_KEY_SEQ_USER_ID, Long.class)
                .get();

        return getForm(seqUserId);
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
        userComponent.executeUpdateUser(dto);

        sessionComponent.removeValue(request.getSession(), SESSION_KEY_FORM);

        if (CommonFlag.of(userSettingForm.getDeleteFlag()).get()) {
            // ユーザ削除時はセッションを削除してログイン画面へ遷移
            sessionComponent.removeValue(request.getSession(), SESSION_KEY_SEQ_USER_ID);
            redirectView(DashboardView.LOGIN);
        }

        return getView(model, DashboardView.ACCOUNT_SETTING_COMPLETE);
    }

    /**
     * ユーザIDからFormを返す
     * 
     * @param seqUserId
     *     ユーザID
     * @return Form
     */
    private UserSettingForm getForm(Long seqUserId) {

        // ユーザ情報取得
        Optional<CompositeUser> entity = userComponent.getUserBySeqUserId(seqUserId);

        UserSettingForm form = new UserSettingForm();
        BeanUtil.copy(entity.get(), form, (src, dest) -> {

            CompositeUser srcUser = (CompositeUser) src;
            UserSettingForm destForm = (UserSettingForm) dest;

            destForm.setGenderType(GenderType.of(srcUser.getGenderType()).getValue());
            destForm.setDeleteFlag(srcUser.isDeleteFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setHeaderFlag(srcUser.isHeaderFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setFooterFlag(srcUser.isFooterFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setMaskFlag(srcUser.isMaskFlag() ? CommonFlag.TRUE.getValue()
                    : CommonFlag.FALSE.getValue());
            destForm.setEnclosureCharFlag(
                    srcUser.isEnclosureCharFlag() ? CommonFlag.TRUE.getValue()
                            : CommonFlag.FALSE.getValue());
        });

        return form;
    }

}
