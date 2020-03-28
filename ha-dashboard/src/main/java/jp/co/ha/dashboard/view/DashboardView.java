package jp.co.ha.dashboard.view;

import jp.co.ha.web.view.BaseView;

/**
 * 健康管理View列挙
 *
 * @version 1.0.0
 */
public enum DashboardView implements BaseView {

    /** ログイン画面 */
    LOGIN("login/index"),
    /** トップ画面 */
    TOP("login/top"),
    /** エラー画面 */
    ERROR("error/index"),

    /** 健康情報入力画面 */
    HEALTH_INFO_INPUT("healthinfo/input"),
    /** 健康情報入力確認画面 */
    HEALTH_INFO_CONFIRM("healthinfo/confirm"),
    /** 健康情報入力完了画面 */
    HEALTH_INFO_COMPLETE("healthinfo/complete"),

    /** 健康情報照会画面 */
    HEALTH_INFO_REFFERNCE("healthinforeference/index"),
    /** 健康情報照会画面 */
    HEALTH_INFO_REF_DETAIL("healthinforeference/detail"),

    /** アカウント設定入力画面 */
    ACCOUNT_SETTING_INPUT("accountsetting/input"),
    /** アカウント設定入力確認画面 */
    ACCOUNT_SETTING_CONFIRM("accountsetting/confirm"),
    /** アカウント設定入力完了画面 */
    ACCOUNT_SETTING_COMPLETE("accountsetting/complete"),

    /** アカウント作成入力画面 */
    ACCOUNT_REGIST_INPUT("accountregist/input"),
    /** アカウント作成入力確認画面 */
    ACCOUNT_REGIST_CONFIRM("accountregist/confirm"),
    /** アカウント作成入力完了画面 */
    ACCOUNT_REGIST_COMPLETE("accountregist/complete"),

    /** 健康情報ファイル入力画面 */
    HEALTH_INFO_FILE_INPUT("healthinfofile/input"),
    /** 健康情報ファイル確認画面 */
    HEALTH_INFO_FILE_CONFIRM("healthinfofile/confirm"),
    /** 健康情報ファイル入力完了画面 */
    HEALTH_INFO_FILE_COMPLETE("healthinfofile/complete"),

    /** カロリー計算画面 */
    CALORIE_CALC("caloriecalc/index"),

    /** お知らせ一覧画面 */
    NEWS_LIST("news/list"),
    /** お知らせ詳細画面 */
    NEWS_DETAIL("news/detail");

    /** パス名 */
    private String name;

    /**
     * コンストラクタ
     *
     * @param name
     *     パス名
     */
    private DashboardView(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

}
