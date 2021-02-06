package jp.co.ha.dashboard.view;

import jp.co.ha.web.view.BaseView;
import jp.co.ha.web.view.BreadcrumbView;

/**
 * 健康管理View列挙
 *
 * @version 1.0.0
 */
public enum DashboardView implements BaseView {

    /** ログイン画面 */
    LOGIN("login/index", "/login/index", DashboardBreadcrumbViewFactory.getDefault()),
    /** トップ画面 */
    TOP("login/top", "/login/top", DashboardBreadcrumbViewFactory.getTop()),
    /** エラー画面 */
    ERROR("error/index", "/error/index", DashboardBreadcrumbViewFactory.getDefault()),

    /** 健康情報入力画面 */
    HEALTH_INFO_INPUT("healthinfo/input", "/healthinfo/input",
            DashboardBreadcrumbViewFactory.getHealthInfo()),
    /** 健康情報入力確認画面 */
    HEALTH_INFO_CONFIRM("healthinfo/confirm", "/healthinfo/confirm",
            DashboardBreadcrumbViewFactory.getHealthInfo()),
    /** 健康情報入力完了画面 */
    HEALTH_INFO_COMPLETE("healthinfo/complete", "/healthinfo/complete",
            DashboardBreadcrumbViewFactory.getHealthInfo()),

    /** 健康情報照会画面 */
    HEALTH_INFO_REFERNCE("healthinforeference/index", "/healthinforeference/index",
            DashboardBreadcrumbViewFactory.getHealthInfoReference()),
    /** 健康情報照会画面 */
    HEALTH_INFO_REF_DETAIL("healthinforeference/detail", "/healthinforeference/detail",
            DashboardBreadcrumbViewFactory.getHealthInfoReference()),

    /** アカウント設定入力画面 */
    ACCOUNT_SETTING_INPUT("accountsetting/input", "/accountsetting/input",
            DashboardBreadcrumbViewFactory.getAccountSetting()),
    /** アカウント設定入力確認画面 */
    ACCOUNT_SETTING_CONFIRM("accountsetting/confirm", "/accountsetting/confirm",
            DashboardBreadcrumbViewFactory.getAccountSetting()),
    /** アカウント設定入力完了画面 */
    ACCOUNT_SETTING_COMPLETE("accountsetting/complete", "/accountsetting/complete",
            DashboardBreadcrumbViewFactory.getAccountSetting()),

    /** アカウント作成入力画面 */
    ACCOUNT_REGIST_INPUT("accountregist/input", "/accountregist/input",
            DashboardBreadcrumbViewFactory.getAccountRegist()),
    /** アカウント作成入力確認画面 */
    ACCOUNT_REGIST_CONFIRM("accountregist/confirm", "/accountregist/confirm",
            DashboardBreadcrumbViewFactory.getAccountRegist()),
    /** アカウント作成入力完了画面 */
    ACCOUNT_REGIST_COMPLETE("accountregist/complete", "/accountregist/complete",
            DashboardBreadcrumbViewFactory.getAccountRegist()),

    /** アカウント回復Index画面 */
    ACCOUNT_RECOVERY_INDEX("accountrecovery/index", "/accountrecovery/index",
            DashboardBreadcrumbViewFactory.getAccountRecovery()),
    /** アカウント回復入力画面 */
    ACCOUNT_RECOVERY_EDIT("accountrecovery/edit", "/accountrecovery/edit",
            DashboardBreadcrumbViewFactory.getAccountRecovery()),
    /** アカウント回復入力確認画面 */
    ACCOUNT_RECOVERY_CONFIRM("accountrecovery/confirm", "/accountrecovery/confirm",
            DashboardBreadcrumbViewFactory.getAccountRecovery()),

    /** 健康情報ファイル入力画面 */
    HEALTH_INFO_FILE_INPUT("healthinfofile/input", "/healthinfofile/input",
            DashboardBreadcrumbViewFactory.getHealthInfoFile()),
    /** 健康情報ファイル確認画面 */
    HEALTH_INFO_FILE_CONFIRM("healthinfofile/confirm", "/healthinfofile/confirm",
            DashboardBreadcrumbViewFactory.getHealthInfoFile()),
    /** 健康情報ファイル入力完了画面 */
    HEALTH_INFO_FILE_COMPLETE("healthinfofile/complete", "/healthinfofile/complete",
            DashboardBreadcrumbViewFactory.getHealthInfoFile()),

    /** カロリー計算画面 */
    CALORIE_CALC("caloriecalc/index", "/caloriecalc/index",
            DashboardBreadcrumbViewFactory.getCalorie()),

    /** 肺活量計算画面 */
    BREATHING_CAPACITY_CALC("breathingcapacity/index", "/breathingcapacity/index",
            DashboardBreadcrumbViewFactory.getBreathingcapacity()),

    /** お知らせ一覧画面 */
    NEWS_LIST("news/list", "/news/list", DashboardBreadcrumbViewFactory.getNewsList());

    /** パス名 */
    private String name;
    /** リダイレクトパス名 */
    private String redirectPath;
    /** パンくず情報 */
    private BreadcrumbView breadcrumbView;

    /**
     * コンストラクタ
     *
     * @param name
     *     パス名
     * @param redirectPath
     *     リダイレクトパス名
     * @param breadcrumbView
     *     パンくず情報View
     */
    private DashboardView(String name, String redirectPath,
            BreadcrumbView breadcrumbView) {
        this.name = name;
        this.redirectPath = redirectPath;
        this.breadcrumbView = breadcrumbView;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRedirectPath() {
        return this.redirectPath;
    }

    @Override
    public BreadcrumbView getBreadcrumbView() {
        return this.breadcrumbView;
    }

}
