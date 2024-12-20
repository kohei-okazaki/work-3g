package jp.co.ha.dashboard.account.controller;

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

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.business.dto.AccountDto;
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
import jp.co.ha.dashboard.account.form.AccountSettingForm;
import jp.co.ha.dashboard.account.service.AccountSettingService;
import jp.co.ha.dashboard.account.service.impl.AccountSettingServiceImpl;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.composite.CompositeAccount;

/**
 * 健康管理_アカウント設定コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("accountsetting")
public class AccountSettingController
        implements BaseWizardController<AccountSettingForm> {

    /** {@linkplain AccountSettingServiceImpl} */
    @Autowired
    private AccountSettingService accountSettingService;
    /** {@linkplain AccountSearchServiceImpl} */
    @Autowired
    private AccountSearchService accountSearchService;
    /** {@linkplain SessionComponent} */
    @Autowired
    private SessionComponent sessionComponent;

    /**
     * Formを返す
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return AccountSettingForm
     */
    @ModelAttribute("accountSettingForm")
    public AccountSettingForm setUpForm(HttpServletRequest request) {

        // セッションからユーザIDを取得
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).get();

        Optional<CompositeAccount> entity = accountSearchService
                .findCompositAccountById(seqUserId);

        AccountSettingForm form = new AccountSettingForm();
        BeanUtil.copy(entity.get(), form, (src, dest) -> {
            CompositeAccount srcAccount = (CompositeAccount) src;
            AccountSettingForm destForm = (AccountSettingForm) dest;
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
    public String confirm(Model model, @Valid AccountSettingForm form,
            BindingResult result, HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            return getView(model, DashboardView.ACCOUNT_SETTING_INPUT);
        }

        // sessionにアカウント設定form情報を保持
        sessionComponent.setValue(request.getSession(), "accountSettingForm", form);

        return getView(model, DashboardView.ACCOUNT_SETTING_CONFIRM);
    }

    @Override
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, AccountSettingForm form,
            HttpServletRequest request) throws BaseException {

        // sessionよりアカウント設定form情報を取得
        AccountSettingForm accountSettingForm = sessionComponent
                .getValue(request.getSession(), "accountSettingForm",
                        AccountSettingForm.class)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

        AccountDto dto = new AccountDto();
        BeanUtil.copy(accountSettingForm, dto);
        dto.setDeleteFlag(CommonFlag.of(accountSettingForm.getDeleteFlag()).get());
        dto.setHeaderFlag(CommonFlag.of(accountSettingForm.getHeaderFlag()).get());
        dto.setFooterFlag(CommonFlag.of(accountSettingForm.getFooterFlag()).get());
        dto.setMaskFlag(CommonFlag.of(accountSettingForm.getMaskFlag()).get());
        dto.setEnclosureCharFlag(
                CommonFlag.of(accountSettingForm.getEnclosureCharFlag()).get());

        // form情報から更新処理を行う
        accountSettingService.execute(dto);

        sessionComponent.removeValue(request.getSession(), "accountSettingForm");

        return getView(model, DashboardView.ACCOUNT_SETTING_COMPLETE);
    }

}
