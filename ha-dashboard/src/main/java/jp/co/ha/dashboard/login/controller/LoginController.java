package jp.co.ha.dashboard.login.controller;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.ha.business.db.crud.read.AccountSearchService;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.impl.AccountSearchServiceImpl;
import jp.co.ha.business.db.crud.read.impl.HealthInfoSearchServiceImpl;
import jp.co.ha.business.healthInfo.HealthInfoGraphModel;
import jp.co.ha.business.healthInfo.service.HealthInfoGraphService;
import jp.co.ha.business.healthInfo.service.impl.HealthInfoGraphServiceImpl;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.business.login.LoginCheck;
import jp.co.ha.business.login.LoginCheckResult;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.BeanUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.util.DateTimeUtil.DateFormatType;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.login.form.LoginForm;
import jp.co.ha.dashboard.view.DashboardView;
import jp.co.ha.db.entity.Account;

/**
 * 健康管理_ログイン画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
public class LoginController implements BaseWebController {

    /** 健康情報検索条件 */
    private static final SelectOption SELECT_OPTION = new SelectOptionBuilder()
            .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC).limit(10).build();
    /** {@linkplain SessionComponent} */
    @Autowired
    private SessionComponent sessionComponent;
    /** {@linkplain AccountSearchServiceImpl} */
    @Autowired
    private AccountSearchService accountSearchService;
    /** {@linkplain HealthInfoSearchServiceImpl} */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** {@linkplain HealthInfoGraphServiceImpl} */
    @Autowired
    private HealthInfoGraphService healthInfoGraphService;
    /** {@linkplain MessageSource} */
    @Autowired
    private MessageSource messageSource;

    /**
     * Formを返す
     *
     * @return LoginForm
     */
    @ModelAttribute("loginForm")
    public LoginForm setUpForm() {
        return new LoginForm();
    }

    /**
     * ログイン画面
     *
     * @param model
     *     {@linkplain Model}
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return ログイン画面
     */
    @NonAuth
    @GetMapping("login")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("isLogout", request.getParameter("isLogout"));

        return getView(DashboardView.LOGIN);
    }

    /**
     * ログアウト処理
     *
     * @param request
     *     {@linkplain HttpServletRequest}
     * @param redirectAttr
     *     {@linkplain RedirectAttributes}
     * @return ログイン画面へリダイレクトする
     */
    @NonAuth
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttr) {

        // ログアウト時にすべてのセッション情報を削除する
        sessionComponent.removeValues(request.getSession());

        redirectAttr.addAttribute("isLogout", true);

        return redirectView(DashboardView.LOGIN);
    }

    /**
     * TOP画面
     *
     * @param model
     *     {@linkplain Model}
     * @param request
     *     {@linkplain HttpServletRequest}
     * @param form
     *     {@linkplain LoginForm}
     * @param result
     *     {@linkplain BindingResult}
     * @return TOP画面
     * @throws BaseException
     *     基底例外
     */
    @NonAuth
    @PostMapping("/top")
    public String top(Model model, HttpServletRequest request, @Valid LoginForm form,
            BindingResult result) throws BaseException {

        if (result.hasErrors()) {
            // validationエラーの場合
            return getView(DashboardView.LOGIN);
        }

        // アカウント情報を検索
        Optional<Account> account = accountSearchService
                .findByMailAddress(form.getMailAddress());
        LoginCheckResult checkResult = new LoginCheck().check(account,
                form.getPassword());
        if (checkResult.hasError()) {
            String errorMessage = messageSource.getMessage(
                    checkResult.getErrorCode().getOuterErrorCode(), null,
                    Locale.getDefault());
            model.addAttribute("errorMessage", errorMessage);
            return getView(DashboardView.LOGIN);
        }

        Long seqUserId = account.get().getSeqUserId();

        // セッションにユーザIDを登録する。
        sessionComponent.setValue(request.getSession(), "seqUserId", seqUserId);

        // 健康情報グラフ作成
        putGraph(model, seqUserId);

        // 健康情報通知設定
        setHealthInfoRegistNotice(model, seqUserId);

        return getView(model, DashboardView.TOP);

    }

    /**
     * TOP画面
     *
     * @param model
     *     {@linkplain Model}
     * @param request
     *     {@linkplain HttpServletRequest}
     * @return TOP画面
     */
    @NonAuth
    @GetMapping("/top")
    public String top(Model model, HttpServletRequest request) {
        // jp.co.ha.business.interceptor.DashboardAuthInterceptorで認証チェックを行うと、
        // ログイン前のアカウント作成画面でヘッダーを踏んだときにログイン情報がなくてコケるのでここでsession情報をチェックする
        Long seqUserId = sessionComponent
                .getValue(request.getSession(), "seqUserId", Long.class).orElse(null);
        if (BeanUtil.isNull(seqUserId)) {
            return getView(DashboardView.LOGIN);
        }

        // 健康情報グラフ作成
        putGraph(model, seqUserId);

        // 健康情報通知設定
        setHealthInfoRegistNotice(model, seqUserId);

        return getView(model, DashboardView.TOP);
    }

    /**
     * 健康情報グラフをModelに追加
     * 
     * @param model
     *     Model
     * @param seqUserId
     *     ユーザID
     */
    private void putGraph(Model model, Long seqUserId) {

        // 健康情報グラフを作成
        healthInfoGraphService.putGraph(model, () -> {

            // 健康情報を降順で先頭10件を検索し、健康情報IDの昇順に並べ替え
            HealthInfoGraphModel graphModel = new HealthInfoGraphModel();
            healthInfoSearchService
                    .findBySeqUserId(seqUserId, SELECT_OPTION).stream()
                    .sorted((o1, o2) -> o1.getSeqHealthInfoId()
                            .compareTo(o2.getSeqHealthInfoId()))
                    .forEach(e -> {
                        graphModel.addHealthInfoRegDate(e.getHealthInfoRegDate(),
                                DateFormatType.YYYYMMDDHHMMSS);
                        graphModel.addWeight(e.getWeight());
                        graphModel.addBmi(e.getBmi());
                        graphModel.addStandardWeight(e.getStandardWeight());
                    });

            return graphModel;
        });
    }

    /**
     * 健康情報が当日未登録通知設定
     * 
     * @param model
     *     Model
     * @param seqUserId
     *     ユーザID
     */
    private void setHealthInfoRegistNotice(Model model, Long seqUserId) {

        // システム日時
        LocalDateTime sysdate = DateTimeUtil.getSysDate();
        LocalDateTime from = DateTimeUtil.getStartDay(sysdate);
        LocalDateTime to = DateTimeUtil.getEndDay(sysdate);

        long count = healthInfoSearchService
                .countBySeqUserIdBetweenHealthInfoRegDate(seqUserId, from, to);

        if (count == 0) {
            // 通知設定
            model.addAttribute("needNotice", "Y");
        }
    }
}
