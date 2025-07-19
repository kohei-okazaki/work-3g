package jp.co.ha.dashboard.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import jp.co.ha.business.api.aws.AwsS3Key;
import jp.co.ha.business.api.aws.AwsSesComponent;
import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.create.UserRecoveryTokenCreateService;
import jp.co.ha.business.db.crud.create.impl.UserRecoveryTokenCreateServiceImpl;
import jp.co.ha.business.db.crud.read.UserRecoveryTokenSearchService;
import jp.co.ha.business.db.crud.read.UserSearchService;
import jp.co.ha.business.db.crud.read.impl.UserRecoveryTokenSearchServiceImpl;
import jp.co.ha.business.db.crud.read.impl.UserSearchServiceImpl;
import jp.co.ha.business.db.crud.update.UserUpdateService;
import jp.co.ha.business.db.crud.update.impl.UserUpdateServiceImpl;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.exception.DashboardErrorCode;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.Sha256HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.user.form.UserRecoveryForm;
import jp.co.ha.dashboard.user.form.UserRecoveryMailAddressInputForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserRecoveryToken;

/**
 * 健康管理_ユーz回復のコントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("accountrecovery")
public class UserRecoveryController implements BaseWebController {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(UserRecoveryController.class);

    /** {@linkplain UserSearchServiceImpl} */
    @Autowired
    private UserSearchService userSearchService;
    /** {@linkplain UserUpdateServiceImpl} */
    @Autowired
    private UserUpdateService userUpdateService;
    /** {@linkplain UserRecoveryTokenCreateServiceImpl} */
    @Autowired
    private UserRecoveryTokenCreateService userRecoveryTokenCreateService;
    /** {@linkplain UserRecoveryTokenSearchServiceImpl} */
    @Autowired
    private UserRecoveryTokenSearchService userRecoveryTokenSearchService;
    /** {@linkplain HealthInfoProperties} */
    @Autowired
    private HealthInfoProperties properties;
    /** {@linkplain Sha256HashEncoder} */
    @Sha256
    @Autowired
    private HashEncoder encoder;
    /** {@linkplain AwsSesComponent} */
    @Autowired
    private AwsSesComponent sesComponent;
    /** {@linkplain UserComponent} */
    @Autowired
    private UserComponent userComponent;

    /**
     * Formを返す
     *
     * @return userRecoveryMailAddressInputForm
     */
    @ModelAttribute("userRecoveryMailAddressInputForm")
    public UserRecoveryMailAddressInputForm userRecoveryMailAddressInputForm() {
        return new UserRecoveryMailAddressInputForm();
    }

    /**
     * Formを返す
     *
     * @return UserRecoveryForm
     */
    @ModelAttribute("userRecoveryForm")
    public UserRecoveryForm userRecoveryForm() {
        return new UserRecoveryForm();
    }

    /**
     * ユーザ回復のメールアドレス入力画面の表示
     *
     * @param model
     *     {@linkplain Model}
     * @return ユーザ回復画面
     */
    @NonAuth
    @GetMapping("/index")
    public String index(Model model) {

        // メールアドレスを入力させるためフラグをtrueに
        model.addAttribute("isInputMailAddress", true);

        return getView(model, DashboardView.ACCOUNT_RECOVERY_INDEX);
    }

    /**
     * ユーザ回復メールアドレスを送信する
     *
     * @param model
     *     {@linkplain Model}
     * @param form
     *     ユーザ回復画面のForm
     * @param result
     *     {@linkplain BindingResult}
     * @return ユーザ回復画面
     * @throws BaseException
     *     メールの送信に失敗した場合
     */
    @NonAuth
    @PostMapping("/sendMail")
    public String sendMail(Model model, @Valid UserRecoveryMailAddressInputForm form,
            BindingResult result) throws BaseException {

        if (result.hasErrors()) {
            // 妥当性チェックエラーの場合
            return getView(model, DashboardView.ACCOUNT_RECOVERY_INDEX);
        }

        // ユーザ情報検索
        User user = userSearchService.findByMailAddress(form.getMailAddress())
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "メールアドレスが未登録です"));

        // ユーザ回復トークン登録
        UserRecoveryToken entity = new UserRecoveryToken();
        entity.setSeqUserId(user.getSeqUserId());
        entity.setToken(encoder.encode(user.getMailAddress(), DateTimeUtil.toString(
                DateTimeUtil.getSysDate(), DateFormatType.YYYYMMDDHHMMSS_NOSEP)));
        entity.setTokenCreateDate(DateTimeUtil.getSysDate());
        userRecoveryTokenCreateService.create(entity);

        // SESでパスワード再設定メールを送信する
        String to = form.getMailAddress();
        String title = "パスワード再設定" + DateTimeUtil.toString(DateTimeUtil.getSysDate(),
                DateTimeUtil.DateFormatType.YYYYMMDD_NOSEP);

        sesComponent.sendMail(to, title, AwsS3Key.ACCOUNT_RECOVERY_TEMPLATE,
                getMailTemplateBody(entity));

        // メールを送信したためフラグをtrueに
        model.addAttribute("isSendMailAddress", true);

        return getView(model, DashboardView.ACCOUNT_RECOVERY_INDEX);
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

        // ユーザ情報検索
        User user = getAccount(seqUserId);

        // ユーザ回復トークンを検索し、トークンが有効であるかを確認する
        @SuppressWarnings("unused")
        UserRecoveryToken accountRecoveryTokenData = userRecoveryTokenSearchService
                .findBySeqUserIdAndTokenAndValidTokenCreateDate(user.getSeqUserId(),
                        strToken)
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR, "トークンが不正または無効です"));

        model.addAttribute("account", user);

        return getView(model, DashboardView.ACCOUNT_RECOVERY_EDIT);
    }

    /**
     * パスワード再設定入力確認画面表示
     *
     * @param model
     *     {@linkplain Model}
     * @param form
     *     パスワード再設定Form
     * @param result
     *     {@linkplain BindingResult}
     * @param seqUserId
     *     ユーザID
     * @return パスワード再設定入力確認画面
     * @throws BaseException
     *     入力エラー
     */
    @NonAuth
    @MultiSubmitToken(factory = true)
    @PostMapping("/confirm/{seq_user_id}")
    public String confirm(Model model, @Valid UserRecoveryForm form,
            BindingResult result,
            @PathVariable(name = "seq_user_id", required = false) Optional<String> seqUserId)
            throws BaseException {

        if (result.hasErrors()) {
            // 妥当性チェックエラー
            LOG.warn(result.getFieldErrors().toString());
            return getView(model, DashboardView.ACCOUNT_RECOVERY_EDIT);
        } else if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "パスワードと確認用パスワードが一致しません");
            return getView(model, DashboardView.ACCOUNT_RECOVERY_EDIT);
        }

        // ユーザ情報検索
        User user = getAccount(seqUserId);

        model.addAttribute("account", user);

        return getView(model, DashboardView.ACCOUNT_RECOVERY_CONFIRM);
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
    public String complete(Model model, UserRecoveryForm form,
            @PathVariable(name = "seq_user_id", required = false) Optional<String> seqUserId,
            RedirectAttributes redirect) throws BaseException {

        LOG.debugBean(form);

        User entity = getAccount(seqUserId);
        entity.setPassword(userComponent.getHashPassword(form.getPassword(),
                form.getMailAddress()));

        // ユーザ情報を更新
        userUpdateService.update(entity);

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
    private Map<String, String> getMailTemplateBody(UserRecoveryToken entity) {

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
     * ユーザ情報を返す<br>
     * 存在しない場合、例外をthrowする
     *
     * @param seqUserId
     *     ユーザID
     * @return ユーザ情報
     * @throws BaseException
     */
    private User getAccount(Optional<String> seqUserId) throws BaseException {

        if (seqUserId == null || !seqUserId.isPresent()
                || !RegexType.HALF_NUMBER.is(seqUserId.get())) {
            // ユーザIDが未指定または半角数字以外の場合
            throw new BusinessException(
                    DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                    "ユーザIDが未指定または半角数字以外です seq_user_id=" + seqUserId);
        }

        // ユーザ情報検索
        return userSearchService.findById(Long.valueOf(seqUserId.get()))
                .orElseThrow(() -> new BusinessException(
                        DashboardErrorCode.ILLEGAL_ACCESS_ERROR,
                        "ユーザIDと紐づくユーザ情報がありません seq_user_id=" + seqUserId));
    }

}
