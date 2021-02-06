package jp.co.ha.dashboard.view;

import jp.co.ha.web.view.BreadcrumbView;
import jp.co.ha.web.view.BreadcrumbView.Breadcrumb;

/**
 * ダッシュボードのパンくず情報ViewのFactoryクラス
 *
 * @version 1.0.0
 */
public class DashboardBreadcrumbViewFactory {

    /**
     * プライベートコンストラクタ
     */
    private DashboardBreadcrumbViewFactory() {
    }

    /**
     * デフォルトのパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getDefault() {
        return new BreadcrumbView();
    }

    /**
     * TOP画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getTop() {
        BreadcrumbView view = new BreadcrumbView();

        Breadcrumb top = new Breadcrumb();
        top.setViewName("TOP");
        top.setUrl("/login/top");
        view.addBreadcrumb(top);

        return view;
    }

    /**
     * 健康情報画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfo() {
        BreadcrumbView view = getTop();

        Breadcrumb healthInfo = new Breadcrumb();
        healthInfo.setViewName("健康情報登録");
        healthInfo.setUrl("/healthinfo/input");
        view.addBreadcrumb(healthInfo);

        return view;
    }

    /**
     * 健康情報照会画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfoReference() {
        BreadcrumbView view = getTop();

        Breadcrumb healthInfoRef = new Breadcrumb();
        healthInfoRef.setViewName("健康情報照会");
        healthInfoRef.setUrl("/healthinforeference/index");
        view.addBreadcrumb(healthInfoRef);

        return view;
    }

    /**
     * アカウント設定画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getAccountSetting() {
        BreadcrumbView view = getTop();

        Breadcrumb accountSetting = new Breadcrumb();
        accountSetting.setViewName("アカウント設定");
        accountSetting.setUrl("/accountsetting/input");
        view.addBreadcrumb(accountSetting);

        return view;
    }

    /**
     * アカウント作成画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getAccountRegist() {
        BreadcrumbView view = new BreadcrumbView();

        Breadcrumb accountRegist = new Breadcrumb();
        accountRegist.setViewName("アカウント作成");
        accountRegist.setUrl("/accountregist/input");
        view.addBreadcrumb(accountRegist);

        return view;
    }

    /**
     * アカウント回復画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getAccountRecovery() {
        BreadcrumbView view = new BreadcrumbView();

        Breadcrumb accountRecovery = new Breadcrumb();
        accountRecovery.setViewName("アカウント回復");
        accountRecovery.setUrl("/accountrecovery/index");
        view.addBreadcrumb(accountRecovery);

        return view;
    }

    /**
     * 健康情報ファイル画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfoFile() {
        BreadcrumbView view = getTop();

        Breadcrumb healthInfoFile = new Breadcrumb();
        healthInfoFile.setViewName("健康情報ファイル");
        healthInfoFile.setUrl("/healthinfofile/input");
        view.addBreadcrumb(healthInfoFile);

        return view;
    }

    /**
     * カロリー計算画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getCalorie() {
        BreadcrumbView view = getTop();

        Breadcrumb calorie = new Breadcrumb();
        calorie.setViewName("カロリー計算");
        calorie.setUrl("/caloriecalc/index");
        view.addBreadcrumb(calorie);

        return view;
    }

    /**
     * 肺活量計算画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getBreathingcapacity() {
        BreadcrumbView view = getTop();

        Breadcrumb breathingcapacity = new Breadcrumb();
        breathingcapacity.setViewName("肺活量計算");
        breathingcapacity.setUrl("/breathingcapacity/index");
        view.addBreadcrumb(breathingcapacity);

        return view;
    }

    /**
     * お知らせ一覧画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getNewsList() {
        BreadcrumbView view = getTop();

        Breadcrumb news = new Breadcrumb();
        news.setViewName("お知らせ");
        news.setUrl("/news/list");
        view.addBreadcrumb(news);

        return view;
    }

}
