package jp.co.ha.dashboard.view;

import jp.co.ha.common.web.view.BreadcrumbView;
import jp.co.ha.common.web.view.BreadcrumbView.Breadcrumb;

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
     * @param isCurrent
     *     現在地
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getTop(boolean isCurrent) {
        BreadcrumbView view = new BreadcrumbView();

        Breadcrumb top = new Breadcrumb();
        top.setViewName("TOP");
        top.setUrl("/top");
        top.setCurrent(isCurrent);
        view.addBreadcrumb(top);

        return view;
    }

    /**
     * 健康情報画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfo() {
        BreadcrumbView view = getTop(false);

        Breadcrumb healthInfo = new Breadcrumb();
        healthInfo.setViewName("健康情報登録");
        healthInfo.setUrl("/healthinfo/input");
        healthInfo.setCurrent(true);
        view.addBreadcrumb(healthInfo);

        return view;
    }

    /**
     * 健康情報照会画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfoReference() {
        return getHealthInfoReference(true);
    }

    /**
     * 健康情報照会画面のパンくずリスト情報Viewを返す
     *
     * @param isCurrent
     *     現在地
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfoReference(boolean isCurrent) {
        BreadcrumbView view = getTop(false);

        Breadcrumb healthInfoRef = new Breadcrumb();
        healthInfoRef.setViewName("健康情報照会");
        healthInfoRef.setUrl("/healthinforeference/index");
        healthInfoRef.setCurrent(isCurrent);
        view.addBreadcrumb(healthInfoRef);

        return view;
    }

    /**
     * 健康情報詳細画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfoDetail() {
        BreadcrumbView view = getHealthInfoReference(false);

        Breadcrumb healthInfoRef = new Breadcrumb();
        healthInfoRef.setViewName("健康情報詳細");
        healthInfoRef.setUrl("/healthinforeference/detail");
        healthInfoRef.setCurrent(true);
        view.addBreadcrumb(healthInfoRef);

        return view;
    }

    /**
     * ユーザ設定画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getAccountSetting() {
        BreadcrumbView view = getTop(false);

        Breadcrumb accountSetting = new Breadcrumb();
        accountSetting.setViewName("ユーザ設定");
        accountSetting.setUrl("/accountsetting/input");
        accountSetting.setCurrent(true);
        view.addBreadcrumb(accountSetting);

        return view;
    }

    /**
     * ユーザ作成画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getAccountRegist() {
        BreadcrumbView view = new BreadcrumbView();

        Breadcrumb accountRegist = new Breadcrumb();
        accountRegist.setViewName("ユーザ作成");
        accountRegist.setUrl("/accountregist/input");
        accountRegist.setCurrent(true);
        view.addBreadcrumb(accountRegist);

        return view;
    }

    /**
     * ユーザ回復画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getAccountRecovery() {
        BreadcrumbView view = new BreadcrumbView();

        Breadcrumb accountRecovery = new Breadcrumb();
        accountRecovery.setViewName("ユーザ回復");
        accountRecovery.setUrl("/accountrecovery/index");
        accountRecovery.setCurrent(true);
        view.addBreadcrumb(accountRecovery);

        return view;
    }

    /**
     * 健康情報ファイル画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getHealthInfoFile() {
        BreadcrumbView view = getTop(false);

        Breadcrumb healthInfoFile = new Breadcrumb();
        healthInfoFile.setViewName("健康情報ファイル");
        healthInfoFile.setUrl("/healthinfofile/input");
        healthInfoFile.setCurrent(true);
        view.addBreadcrumb(healthInfoFile);

        return view;
    }

    /**
     * カロリー計算画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getCalorie() {
        BreadcrumbView view = getTop(false);

        Breadcrumb calorie = new Breadcrumb();
        calorie.setViewName("カロリー計算");
        calorie.setUrl("/caloriecalc/index");
        calorie.setCurrent(true);
        view.addBreadcrumb(calorie);

        return view;
    }

    /**
     * 肺活量計算画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getBreathingcapacity() {
        BreadcrumbView view = getTop(false);

        Breadcrumb breathingcapacity = new Breadcrumb();
        breathingcapacity.setViewName("肺活量計算");
        breathingcapacity.setUrl("/breathingcapacity/index");
        breathingcapacity.setCurrent(true);
        view.addBreadcrumb(breathingcapacity);

        return view;
    }

    /**
     * お知らせ一覧画面のパンくずリスト情報Viewを返す
     *
     * @return パンくずリスト情報View
     */
    public static BreadcrumbView getNewsList() {
        BreadcrumbView view = getTop(false);

        Breadcrumb news = new Breadcrumb();
        news.setViewName("お知らせ");
        news.setUrl("/news/list");
        news.setCurrent(true);
        view.addBreadcrumb(news);

        return view;
    }

}
