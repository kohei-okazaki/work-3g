package jp.co.ha.dashboard.account.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.component.AccountComponent;
import jp.co.ha.business.db.crud.create.AccountRecoveryTokenCreateService;
import jp.co.ha.business.db.crud.read.AccountRecoveryTokenSearchService;
import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.update.AccountUpdateService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.dashboard.account.form.AccountRecoveryForm;
import jp.co.ha.dashboard.account.form.AccountRecoveryMailAddressInputForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.Account;
import jp.co.ha.db.entity.AccountRecoveryToken;
import jp.co.ha.web.controller.BaseWebController;

/**
 * アカウント回復のコントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("accountrecovery")
public class AccountRecoveryController
        implements BaseWebController {

    /** 健康情報登録メールテンプレートID */
    private static final String TEMPLATE_ID = "mail-template/account-recovery-template.txt";
    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(AccountRecoveryController.class);

    /** アカウント検索サービス */
    @Autowired
    private AccountSearchService accountSearchService;
    /** アカウント更新サービス */
    @Autowired
    private AccountUpdateService accountUpdateService;
    /** アカウント回復トークン作成サービス */
    @Autowired
    private AccountRecoveryTokenCreateService accountRecoveryTokenCreateService;
    /** アカウント回復トークン検索サービス */
    @Autowired
    private AccountRecoveryTokenSearchService accountRecoveryTokenSearchService;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties properties;
    /** SHA-256作成Encoder */
    @Autowired
    @Qualifier("sha256HashEncoder")
    private HashEncoder encoder;
    /** AwsSesComponent */
    @Autowired
    private AwsSesComponent sesComponent;
    /** AccountComponent */
    @Autowired
    private AccountComponent accountComponent;

    /**
     * Formを返す
     *
     * @return AccountRecoveryMailAddressInputForm
     */
    @ModelAttribute("accountRecoveryMailAddressInputForm")
    public AccountRecoveryMailAddressInputForm accountRecoveryMailAddressInputForm() {
        return new AccountRecoveryMailAddressInputForm();
    }

    /**
     * Formを返す
     *
     * @return AccountRecoveryForm
     */
    @ModelAttribute("accountRecoveryForm")
    public AccountRecoveryForm accountRecoveryForm() {
        return new AccountRecoveryForm();
    }

    /**
     * アカウント回復のメールアドレス入力画面の表示
     *
     * @param model
     *     {@linkplain Model}
     * @return アカウント回復画面
     */
    @NonAuth
    @GetMapping("/index")
    public String index(Model model) {

        // メールアドレスを入力させるためフラグをtrueに
        model.addAttribute("isInputMailAddress", true);

        return getView(DashboardView.ACCOUNT_RECOVERY_INDEX);
    }

    /**
     * アカウント回復メールアドレスを送信する
     *
     * @param model
     *     {@linkplain Model}
     * @param form
     *     アカウント回復画面のForm
     * @param result
     *     {@linkplain BindingResult}
     * @return アカウント回復画面
     * @throws BaseException
     *     メールの送信に失敗した場合
     */
    @NonAuth
    @PostMapping("/sendMail")
    public String sendMail(Model model, @Valid AccountRecoveryMailAddressInputForm form,
            BindingResult result) throws BaseException {

        if (result.hasErrors()) {
            // 妥当性チェックエラーの場合
            return getView(DashboardView.ACCOUNT_RECOVERY_INDEX);
        }

        // アカウント情報検索
        Account account = accountSearchService.findByMailAddress(form.getMailAddress())
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "メールアドレスが未登録です"));

        // アカウント回復トークン登録
        AccountRecoveryToken entity = new AccountRecoveryToken();
        entity.setSeqUserId(account.getSeqUserId());
        entity.setToken(encoder.encode(account.getMailAddress(), DateTimeUtil
                .toString(DateTimeUtil.getSysDate(),
                        DateFormatType.YYYYMMDDHHMMSS_NOSEP)));
        entity.setTokenCreateDate(DateTimeUtil.getSysDate());
        accountRecoveryTokenCreateService.create(entity);

        // SESでパスワード再設定メールを送信する
        String to = form.getMailAddress();
        String title = "パスワード再設定" + DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                DateTimeUtil.DateFormatType.YYYYMMDD_NOSEP);

        sesComponent.sendMail(to, title, TEMPLATE_ID,
                getMailTemplateBody(entity));

        // メールを送信したためフラグをtrueに
        model.addAttribute("isSendMailAddress", true);

        return getView(DashboardView.ACCOUNT_RECOVERY_INDEX);
    }

    /**
     * パスワード再設定入力画面表示
     *
     * @param model
     *     {@linkplain Model}
     * @param seqUserId
     *     ユーザID
     * @param token
     *     トークン
     * @return パスワード再設定入力画面
     * @throws BaseException
     *     ユーザIDやトークンが不正な場合
     */
    @NonAuth
    @GetMapping("/edit/{seq_user_id}")
    public String edit(Model model,
            @PathVariable(name = "seq_user_id", required = false) Optional<String> seqUserId,
            @RequestParam(name = "token", required = false) Optional<String> token)
            throws BaseException {

        // 不正リクエストエラーのチェック処理を追加
        String strToken = token.orElseThrow(() -> new BusinessException(
                DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "トークンが未指定です"));

        if (seqUserId == null || !seqUserId.isPresent()
                || !RegexType.HALF_NUMBER.is(seqUserId.get())) {
            // ユーザIDが未指定または半角数字以外の場合
            throw new BusinessException(
                    DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                    "ユーザIDが未指定または半角数字以外です seq_user_id=" + seqUserId);
        }

        // アカウント情報検索
        Account account = getAccount(seqUserId);

        // 上のアカウント検索ができているのであれば、そのままOptional#getをしても問題ない
        Integer userId = Integer.valueOf(seqUserId.get());
        // アカウント回復トークンを検索し、トークンが有効であるかを確認する
        AccountRecoveryToken accountRecoveryToken = accountRecoveryTokenSearchService
                .findBySeqUserIdAndTokenAndValidTokenCreateDate(userId, strToken)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "トークンが不正または無効です"));

        model.addAttribute("account", account);

        return getView(DashboardView.ACCOUNT_RECOVERY_EDIT);
    }

    /**
     * パスワード再設定入力画面表示
     *
     * @param model
     *     {@linkplain Model}
     * @param form
     *     パスワード再設定Form
     * @param result
     *     {@linkplain BindingResult}
     * @param seqUserId
     *     ユーザID
     * @return パスワード再設定入力画面
     * @throws BaseException
     *     入力エラー
     */
    @NonAuth
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm/{seq_user_id}")
    public String confirm(Model model, @Valid AccountRecoveryForm form,
            BindingResult result,
            @PathVariable(name = "seq_user_id", required = false) Optional<String> seqUserId)
            throws BaseException {

        if (result.hasErrors()) {
            // 妥当性チェックエラー
            LOG.warn(result.getFieldErrors().toString());
            return getView(DashboardView.ACCOUNT_RECOVERY_EDIT);
        } else if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "パスワードと確認用パスワードが一致しません");
            return getView(DashboardView.ACCOUNT_RECOVERY_EDIT);
        }

        // アカウント情報検索
        Account account = getAccount(seqUserId);

        model.addAttribute("account", account);

        return getView(DashboardView.ACCOUNT_RECOVERY_CONFIRM);
    }

    /**
     * パスワード再設定処理を行い、完了画面を表示
     *
     * @param model
     *     {@linkplain Model}
     * @param form
     *     パスワード再設定Form
     * @param seqUserId
     *     ユーザID
     * @param redirect
     *     {@linkplain RedirectAttributes}
     * @return パスワード再設定入力画面
     * @throws BaseException
     *     入力エラー
     */
    @NonAuth
    @MultiSubmitToken(check = true)
    @PostMapping("/complete/{seq_user_id}")
    public String complete(Model model, AccountRecoveryForm form,
            @PathVariable(name = "seq_user_id", required = false) Optional<String> seqUserId,
            RedirectAttributes redirect) throws BaseException {

        LOG.debugRes(form);

        Account entity = getAccount(seqUserId);
        entity.setPassword(accountComponent.getHashPassword(form.getPassword(),
                form.getMailAddress()));

        // アカウント情報を更新
        accountUpdateService.update(entity);

        // ログイン画面にリダイレクト
        redirect.addFlashAttribute("isPasswordReset", true);

        return redirectView(DashboardView.LOGIN);
    }

    /**
     * メールテンプレートBodyを返す
     *
     * @param entity
     *     トークン
     * @return メールテンプレートBody
     */
    private Map<String, String> getMailTemplateBody(AccountRecoveryToken entity) {

        Map<String, String> bodyMap = new HashMap<>();
        // URL
        bodyMap.put("${url}", properties.getHealthInfoDashboardUrl());
        // ユーザID
        bodyMap.put("${seq_user_id}", entity.getSeqUserId().toString());
        // トークン
        bodyMap.put("${token}", entity.getToken());
        return bodyMap;
    }

    /**
     * アカウント情報を返す<br>
     * 存在しない場合、例外をthrowする
     *
     * @param seqUserId
     *     ユーザID
     * @return アカウント情報
     * @throws BaseException
     */
    private Account getAccount(Optional<String> seqUserId) throws BaseException {

        if (seqUserId == null || !seqUserId.isPresent()
                || !RegexType.HALF_NUMBER.is(seqUserId.get())) {
            // ユーザIDが未指定または半角数字以外の場合
            throw new BusinessException(
                    DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                    "ユーザIDが未指定または半角数字以外です seq_user_id=" + seqUserId);
        }

        Integer userId = Integer.valueOf(seqUserId.get());

        // アカウント情報検索
        return accountSearchService.findById(userId)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                        "ユーザIDと紐づくアカウント情報がありません"));
    }

}
