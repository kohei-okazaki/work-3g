package jp.co.ha.dashboard.login.controller;

import static jp.co.ha.business.healthInfo.type.HealthInfoStatus.*;
import static jp.co.ha.common.util.DateTimeUtil.DateFormatType.*;
import static jp.co.ha.dashboard.view.DashboardView.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

import jp.co.ha.business.component.UserComponent;
import jp.co.ha.business.db.crud.read.HealthInfoSearchService;
import jp.co.ha.business.db.crud.read.UserHealthGoalSelectService;
import jp.co.ha.business.healthInfo.HealthInfoGraphModel;
import jp.co.ha.business.healthInfo.service.HealthInfoGraphService;
import jp.co.ha.business.interceptor.annotation.NonAuth;
import jp.co.ha.business.login.LoginCheck;
import jp.co.ha.business.login.LoginCheckResult;
import jp.co.ha.common.db.SelectOption;
import jp.co.ha.common.db.SelectOption.SelectOptionBuilder;
import jp.co.ha.common.db.SelectOption.SortType;
import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.system.SessionComponent;
import jp.co.ha.common.util.CollectionUtil;
import jp.co.ha.common.util.DateTimeUtil;
import jp.co.ha.common.web.controller.BaseWebController;
import jp.co.ha.dashboard.login.form.LoginForm;
import jp.co.ha.db.entity.HealthInfo;
import jp.co.ha.db.entity.User;
import jp.co.ha.db.entity.UserHealthGoal;

/**
 * 健康管理_ログイン画面コントローラ
 *
 * @version 1.0.0
 */
@Controller
public class LoginController implements BaseWebController {

    /** セッションキー：ユーザID */
    private static final String SESSION_KEY_SEQ_USER_ID = "seqUserId";
    /** 健康情報検索条件 */
    private static final SelectOption SELECT_OPTION = new SelectOptionBuilder()
            .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC).limit(10).build();
    /** 健康情報検索条件：直近2件 */
    private static final SelectOption SELECT_OPTION＿LATEST = new SelectOptionBuilder()
            .orderBy("HEALTH_INFO_REG_DATE", SortType.DESC).limit(2).build();

    /** セッションComponent */
    @Autowired
    private SessionComponent sessionComponent;
    /** ユーザComponent */
    @Autowired
    private UserComponent userComponent;
    /** 健康情報検索サービス */
    @Autowired
    private HealthInfoSearchService healthInfoSearchService;
    /** ユーザ健康目標情報検索サービス */
    @Autowired
    private UserHealthGoalSelectService userHealthGoalSelectService;
    /** 健康情報グラフサービス */
    @Autowired
    private HealthInfoGraphService healthInfoGraphService;
    /** メッセージプロパティ */
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

        return getView(LOGIN);
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

        return redirectView(LOGIN);
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
            return getView(LOGIN);
        }

        // ユーザ情報を検索
        Optional<User> user = userComponent.findByMailAddress(form.getMailAddress());
        LoginCheckResult checkResult = new LoginCheck().check(user,
                form.getPassword());
        if (checkResult.hasError()) {
            String errorMessage = messageSource.getMessage(
                    checkResult.getErrorCode().getOuterErrorCode(), null,
                    Locale.getDefault());
            model.addAttribute("errorMessage", errorMessage);
            return getView(LOGIN);
        }

        Long seqUserId = user.get().getSeqUserId();

        // セッションにユーザIDを登録する。
        sessionComponent.setValue(request.getSession(), SESSION_KEY_SEQ_USER_ID,
                seqUserId);

        List<HealthInfo> list = getLastestHealthInfoList(seqUserId);
        HealthInfo latest = getLastesthealthInfo(list);
        HealthInfo previous = getPreviousHealthInfo(list);

        // 最新健康情報設定
        setLatestHealthInfo(model, latest, previous);

        // 目標健康情報設定
        setGoalHealthInfo(model, latest, seqUserId);

        // 健康情報グラフ作成
        putGraph(model, seqUserId);

        // 健康情報通知設定
        setHealthInfoRegistNotice(model, seqUserId);

        return getView(model, TOP);

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
        // ログイン前のユーザ作成画面でヘッダーを踏んだときにログイン情報がなくてコケるのでここでsession情報をチェックする
        Optional<Long> nullableSeqUserId = sessionComponent
                .getValue(request.getSession(), SESSION_KEY_SEQ_USER_ID, Long.class);
        if (!nullableSeqUserId.isPresent()) {
            return getView(LOGIN);
        }
        Long seqUserId = nullableSeqUserId.get();

        List<HealthInfo> list = getLastestHealthInfoList(seqUserId);
        HealthInfo latest = getLastesthealthInfo(list);
        HealthInfo previous = getPreviousHealthInfo(list);

        // 最新健康情報設定
        setLatestHealthInfo(model, latest, previous);

        // 目標健康情報設定
        setGoalHealthInfo(model, latest, seqUserId);

        // 健康情報グラフ作成
        putGraph(model, seqUserId);

        // 健康情報通知設定
        setHealthInfoRegistNotice(model, seqUserId);

        return getView(model, TOP);
    }

    /**
     * 健康情報リストを返す
     * 
     * @param seqUserId
     *     ユーザID
     * @return 健康情報リスト
     */
    private List<HealthInfo> getLastestHealthInfoList(Long seqUserId) {
        return getLatestHealthInfoList(seqUserId);
    }

    /**
     * 最新の健康情報を取得
     * 
     * @param list
     *     健康情報リスト
     * @return 最新の健康情報
     */
    private HealthInfo getLastesthealthInfo(List<HealthInfo> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 最新より1件過去の健康情報を取得
     * 
     * @param list
     *     健康情報リスト
     * @return 最新より1件過去の最新の健康情報
     */
    private HealthInfo getPreviousHealthInfo(List<HealthInfo> list) {
        if (CollectionUtil.isEmpty(list) || list.size() < 2) {
            return null;
        }
        return list.get(1);
    }

    /**
     * 最新の健康情報をModelへ設定する<br>
     * 健康情報が未登録の場合、未登録用のデータをModelへ設定する
     * 
     * @param model
     *     Model
     * @param latest
     *     最新の健康情報
     * @param previous
     *     最新より1件過去の健康情報
     */
    private void setLatestHealthInfo(Model model, HealthInfo latest,
            HealthInfo previous) {

        if (latest == null) {
            // 最新健康情報と最新の1つ過去の健康情報が登録されていない場合
            model.addAttribute("latestHealthInfoNotExistMessage", "健康情報が未登録です。");

        } else if (previous == null) {
            // 最新健康情報のみ登録されている場合
            model.addAttribute("latest", latest);

        } else {

            model.addAttribute("latest", latest);

            BigDecimal latestWeight = latest.getWeight();
            BigDecimal previousWeight = previous.getWeight();

            int compare = latestWeight.compareTo(previousWeight);
            BigDecimal diff = null;
            switch (compare) {
            case -1:
                // latest < previous：減少
                diff = previousWeight.subtract(latestWeight);
                model.addAttribute("diff", diff.stripTrailingZeros().toPlainString());
                model.addAttribute("status", DOWN.getValue());
                break;
            case 1:
                // latest > previous：増加
                diff = latestWeight.subtract(previousWeight);
                model.addAttribute("diff", diff.stripTrailingZeros().toPlainString());
                model.addAttribute("status", INCREASE.getValue());
                break;
            default:
                model.addAttribute("diff", 0);
                model.addAttribute("status", EVEN.getValue());
                break;
            }
        }
    }

    /**
     * 健康情報目標情報をModelへ設定する
     * 
     * @param model
     *     Model
     * @param latest
     *     最新の健康情報
     * @param seqUserId
     *     ユーザID
     */
    private void setGoalHealthInfo(Model model, HealthInfo latest, Long seqUserId) {

        // ユーザ健康目標情報
        UserHealthGoal goal = userHealthGoalSelectService
                .findEnableById(seqUserId).get();
        model.addAttribute("goalWeight", goal.getWeight());

        if (latest != null) {
            // 健康情報の登録が0件の場合

            model.addAttribute("goalDiff",
                    goal.getWeight().subtract(latest.getWeight()));
        }
    }

    /**
     * 最新の健康情報を取得する<br>
     * <ul>
     * <li>最新の健康情報</li>
     * <li>最新の1件過去の健康情報</li>
     * </ul>
     * 
     * @param seqUserId
     *     ユーザID
     * @return 健康情報
     */
    private List<HealthInfo> getLatestHealthInfoList(Long seqUserId) {
        return healthInfoSearchService.findBySeqUserId(seqUserId,
                SELECT_OPTION＿LATEST);
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

            // 健康情報を健康情報登録日時の降順で先頭10件を検索し、健康情報IDの昇順に並べ替え
            HealthInfoGraphModel graphModel = new HealthInfoGraphModel();
            healthInfoSearchService
                    .findBySeqUserId(seqUserId, SELECT_OPTION).stream()
                    .sorted((o1, o2) -> o1.getSeqHealthInfoId()
                            .compareTo(o2.getSeqHealthInfoId()))
                    .forEach(e -> {
                        graphModel.addHealthInfoRegDate(e.getHealthInfoRegDate(),
                                YYYYMMDDHHMMSS);
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

        long count = healthInfoSearchService
                .countBySeqUserIdBetweenHealthInfoRegDate(seqUserId,
                        DateTimeUtil.getStartDay(sysdate),
                        DateTimeUtil.getEndDay(sysdate));

        if (count == 0) {
            // 通知設定
            model.addAttribute("needNotice", "Y");
        }
    }
}
