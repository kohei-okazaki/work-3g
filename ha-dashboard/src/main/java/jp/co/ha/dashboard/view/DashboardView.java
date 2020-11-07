package jp.co.ha.dashboard.view;

import jp.co.ha.web.view.BaseView;

/**
 * 健康管理View列挙
 *
 * @version 1.0.0
 */
public enum DashboardView implements BaseView {

    /** ログイン画面 */
    LOGIN("login/index", "/login/index"),
    /** トップ画面 */
    TOP("login/top", "/login/top"),
    /** エラー画面 */
    ERROR("error/index", "/error/index"),

    /** 健康情報入力画面 */
    HEALTH_INFO_INPUT("healthinfo/input", "/healthinfo/input"),
    /** 健康情報入力確認画面 */
    HEALTH_INFO_CONFIRM("healthinfo/confirm", "/healthinfo/confirm"),
    /** 健康情報入力完了画面 */
    HEALTH_INFO_COMPLETE("healthinfo/complete", "/healthinfo/complete"),

    /** 健康情報照会画面 */
    HEALTH_INFO_REFFERNCE("healthinforeference/index", "/healthinforeference/index"),
    /** 健康情報照会画面 */
    HEALTH_INFO_REF_DETAIL("healthinforeference/detail", "/healthinforeference/detail"),

    /** アカウント設定入力画面 */
    ACCOUNT_SETTING_INPUT("accountsetting/input", "/accountsetting/input"),
    /** アカウント設定入力確認画面 */
    ACCOUNT_SETTING_CONFIRM("accountsetting/confirm", "/accountsetting/confirm"),
    /** アカウント設定入力完了画面 */
    ACCOUNT_SETTING_COMPLETE("accountsetting/complete", "/accountsetting/complete"),

    /** アカウント作成入力画面 */
    ACCOUNT_REGIST_INPUT("accountregist/input", "/accountregist/input"),
    /** アカウント作成入力確認画面 */
    ACCOUNT_REGIST_CONFIRM("accountregist/confirm", "/accountregist/confirm"),
    /** アカウント作成入力完了画面 */
    ACCOUNT_REGIST_COMPLETE("accountregist/complete", "/accountregist/complete"),

    /** アカウント回復Index画面 */
    ACCOUNT_RECOVERY_INDEX("accountrecovery/index", "/accountrecovery/index"),
    /** アカウント回復入力画面 */
    ACCOUNT_RECOVERY_EDIT("accountrecovery/edit", "/accountrecovery/edit"),
    /** アカウント回復入力確認画面 */
    ACCOUNT_RECOVERY_CONFIRM("accountrecovery/confirm", "/accountrecovery/confirm"),

    /** 健康情報ファイル入力画面 */
    HEALTH_INFO_FILE_INPUT("healthinfofile/input", "/healthinfofile/input"),
    /** 健康情報ファイル確認画面 */
    HEALTH_INFO_FILE_CONFIRM("healthinfofile/confirm", "/healthinfofile/confirm"),
    /** 健康情報ファイル入力完了画面 */
    HEALTH_INFO_FILE_COMPLETE("healthinfofile/complete", "/healthinfofile/complete"),

    /** カロリー計算画面 */
    CALORIE_CALC("caloriecalc/index", "/caloriecalc/index"),

    /** 肺活量計算画面 */
    BREATHING_CAPACITY_CALC("breathingcapacity/index", "/breathingcapacity/index"),

    /** お知らせ一覧画面 */
    NEWS_LIST("news/list", "/news/list"),
    /** お知らせ詳細画面 */
    NEWS_DETAIL("news/detail", "/news/detail");

    /** パス名 */
    private String name;
    /** リダイレクトパス名 */
    private String redirectPath;

    /**
     * コンストラクタ
     *
     * @param name
     *     パス名
     * @param redirectPath
     *     リダイレクトパス名
     */
    private DashboardView(String name, String redirectPath) {
        this.name = name;
        this.redirectPath = redirectPath;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRedirectPath() {
        return this.redirectPath;
    }

}
