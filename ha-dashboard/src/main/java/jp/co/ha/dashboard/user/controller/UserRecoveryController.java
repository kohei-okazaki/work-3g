package jp.co.ha.dashboard.user.controller;

import static jp.co.ha.business.exception.DashboardErrorCode.*;
import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;
import static jp.co.ha.dashboard.view.DashboardView.*;

import java.time.LocalDateTime;
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

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.create.UserRecoveryTokenCreateService;
import jp.co.ha.business.db.crud.read.UserRecoveryTokenSearchService;
import jp.co.ha.business.db.crud.update.UserUpdateService;
import jp.co.ha.business.exception.BusinessException;
import jp.co.ha.business.interceptor.annotation.MultiSubmitToken;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.business.io.file.properties.HealthInfoProperties;
import jp.co.ha.common.aws.AwsSesComponent;
import jp.co.ha.common.aws.AwsSesComponent.MailTemplateKey;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.io.encodeanddecode.HashEncoder;
import jp.co.ha.common.io.encodeanddecode.annotation.Sha256;
import jp.co.ha.common.type.RegexType;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.user.form.UserRecoveryForm;
import jp.co.ha.dashboard.user.form.UserRecoveryMailAddressInputForm;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserRecoveryToken;

/**
 * 健康管理_ユーザ回復のコントローラ
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("accountrecovery")
public class UserRecoveryController implements BaseWebController {

    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;
    /** ユーザ情報更新サービス */
    @Autowired
    private UserUpdateService userUpdateService;
    /** ユーザ情報更新サービス */
    @Autowired
    private UserRecoveryTokenCreateService userRecoveryTokenCreateService;
    /** ユーザ回復トークン作成サービスインターフェース */
    @Autowired
    private UserRecoveryTokenSearchService userRecoveryTokenSearchService;
    /** 健康情報設定ファイル */
    @Autowired
    private HealthInfoProperties properties;
    /** SHA-256ハッシュ値作成クラス */
    @Sha256
    @Autowired
    private HashEncoder encoder;
    /** AWS-SES Component */
    @Autowired
    private AwsSesComponent ses;

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

        return getView(model, ACCOUNT_RECOVERY_INDEX);
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
            return getView(model, ACCOUNT_RECOVERY_INDEX);
        }

        // ユーザ情報検索
        User user = userComponent.findByMailAddress(form.getMailAddress())
                .orElseThrow(() -> new BusinessException(
                        ILLEGAL_ACCESS_ERROR, "メールアドレスが未登録です"));

        // システム日時
        LocalDateTime sysdate = DateTimeUtil.getSysDate();
        // ユーザ回復トークン登録
        UserRecoveryToken entity = new UserRecoveryToken();
        entity.setSeqUserId(user.getSeqUserId());
        entity.setToken(encoder.encode(user.getMailAddress(),
                DateTimeUtil.toString(sysdate, YYYYMMDDHHMMSS_NOSEP)));
        entity.setTokenCreateDate(sysdate);
        userRecoveryTokenCreateService.create(entity);

        // SESでパスワード再設定メールを送信する
        String to = form.getMailAddress();
        String title = "パスワード再設定"
                + DateTimeUtil.toString(sysdate, YYYYMMDD_NOSEP);

        ses.sendMail(to, title, MailTemplateKey.ACCOUNT_RECOVERY_TEMPLATE,
                getMailTemplateBody(entity));

        // メールを送信したためフラグをtrueに
        model.addAttribute("isSendMailAddress", true);

        return getView(model, ACCOUNT_RECOVERY_INDEX);
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
                ILLEGAL_ACCESS_ERROR, "トークンが未指定です"));

        // ユーザ情報検索
        User user = getUser(seqUserId);

        // ユーザ回復トークンを検索し、トークンが有効であるかを確認する
        @SuppressWarnings("unused")
        UserRecoveryToken userRecoveryToken = userRecoveryTokenSearchService
                .findBySeqUserIdAndTokenAndValidTokenCreateDate(user.getSeqUserId(),
                        strToken)
                .orElseThrow(() -> new BusinessException(
                        ILLEGAL_ACCESS_ERROR, "トークンが不正または無効です"));

        model.addAttribute("account", user);

        return getView(model, ACCOUNT_RECOVERY_EDIT);
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
    public String confirm(Model model, @Valid UserRecoveryForm form, BindingResult result,
            @PathVariable(name = "seq_user_id", required = false) Optional<String> seqUserId)
            throws BaseException {

        if (result.hasErrors()) {
            // 妥当性チェックエラー
            return getView(model, ACCOUNT_RECOVERY_EDIT);
        } else if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "パスワードと確認用パスワードが一致しません");
            return getView(model, ACCOUNT_RECOVERY_EDIT);
        }

        // ユーザ情報検索
        User user = getUser(seqUserId);

        model.addAttribute("account", user);

        return getView(model, ACCOUNT_RECOVERY_CONFIRM);
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

        User entity = getUser(seqUserId);
        entity.setPassword(userComponent.getHashPassword(form.getPassword(),
                form.getMailAddress()));

        // ユーザ情報を更新
        userUpdateService.update(entity);

        // ログイン画面にリダイレクト
        redirect.addFlashAttribute("isPasswordReset", true);

        return redirectView(LOGIN);
    }

    /**
     * メールテンプレートBodyを返す
     *
     * @param entity
     *     トークン
     * @return メールテンプレートBody
     */
    private Map<String, String> getMailTemplateBody(UserRecoveryToken entity) {
        return Map.of(
                "url", properties.getHealthInfoDashboardUrl(),
                "seq_user_id", entity.getSeqUserId().toString(),
                "token", entity.getToken());
    }

    /**
     * ユーザ情報を返す<br>
     * 存在しない場合、例外をthrowする
     *
     * @param seqUserId
     *     ユーザID
     * @return ユーザ情報
     * @throws BaseException
     *     ユーザ情報取得失敗
     */
    private User getUser(Optional<String> seqUserId) throws BaseException {

        if (seqUserId == null || !seqUserId.isPresent()
                || !RegexType.HALF_NUMBER.is(seqUserId.get())) {
            // ユーザIDが未指定または半角数字以外の場合
            throw new BusinessException(ILLEGAL_ACCESS_ERROR,
                    "ユーザIDが未指定または半角数字以外です seq_user_id=" + seqUserId);
        }

        // ユーザ情報検索
        return userComponent.findById(Long.valueOf(seqUserId.get()))
                .orElseThrow(() -> new BusinessException(ILLEGAL_ACCESS_ERROR,
                        "ユーザIDと紐づくユーザ情報がありません seq_user_id=" + seqUserId));
    }

}
