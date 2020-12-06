package jp.co.ha.dashboard.account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.dto.AccountDto;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.dashboard.account.form.AccountRegistForm;
import jp.co.ha.dashboard.account.service.AccountRegistService;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.web.controller.BaseWizardController;

/**
 * 健康管理_アカウント登録画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("accountregist")
public class AccountRegistController implements BaseWizardController<AccountRegistForm> {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(AccountRegistController.class);
    /** アカウント登録画面サービス */
    @Autowired
    private AccountRegistService accountRegistService;
    /** アカウント検索サービス */
    @Autowired
    private AccountSearchService accountSearchService;
    /** SessionComponent */
    @Autowired
    private SessionComponent sessionComponent;

    /**
     * Formを返す
     *
     * @return AccountRegistForm
     */
    @ModelAttribute("accountRegistForm")
    public AccountRegistForm setUpForm() {
        return new AccountRegistForm();
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
    public String confirm(Model model, @Valid AccountRegistForm form,
            BindingResult result, HttpServletRequest request) throws BaseException {

        if (result.hasErrors()) {
            return getView(model, DashboardView.ACCOUNT_REGIST_INPUT);
        }

        if (accountSearchService.isExistByMailAddress(form.getMailAddress())) {
            model.addAttribute("errorMessage", "指定されたメールアドレスは既に登録されています");
            LOG.warn("指定されたメールアドレスは既に登録されています. mail_address:" + form.getMailAddress());
            return getView(DashboardView.ACCOUNT_REGIST_INPUT);
        }

        // sessionにアカウント登録Form情報を保持
        sessionComponent.setValue(request.getSession(), "accountRegistForm", form);

        return getView(model, DashboardView.ACCOUNT_REGIST_CONFIRM);
    }

    @Override
    @NonAuth
    @MultiSubmitToken(check = true)
    @PostMapping("/complete")
    public String complete(Model model, AccountRegistForm form,
            HttpServletRequest request) throws BaseException {

        // sessionよりアカウント登録Form情報を取得
        AccountRegistForm accountRegistForm = sessionComponent
                .getValue(request.getSession(), "accountRegistForm",
                        AccountRegistForm.class)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "不正リクエストエラーです"));

        AccountDto dto = new AccountDto();
        BeanUtil.copy(accountRegistForm, dto);

        // Form情報から登録処理を行う
        accountRegistService.regist(dto);

        sessionComponent.removeValue(request.getSession(), "accountRegistForm");

        return getView(model, DashboardView.ACCOUNT_REGIST_COMPLETE);
    }

}
