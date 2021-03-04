package jp.co.ha.web.view;

import java.util.ArrayList;
import java.util.List;

/**
 * パンくずView情報
 *
 * @version 1.0.0
 */
public class BreadcrumbView {

    /** パンくずリスト */
    private List<Breadcrumb> breadcrumbList = new ArrayList<>();

    /**
     * breadcrumbListを返す
     *
     * @return breadcrumbList
     */
    public List<Breadcrumb> getBreadcrumbList() {
        return breadcrumbList;
    }

    /**
     * breadcrumbListを設定する
     *
     * @param breadcrumbList
     *     パンくずリスト
     */
    public void setBreadcrumbList(List<Breadcrumb> breadcrumbList) {
        this.breadcrumbList = breadcrumbList;
    }

    /**
     * パンくずリストに追加
     *
     * @param breadcrumb
     *     パンくず情報
     */
    public void addBreadcrumb(Breadcrumb breadcrumb) {
        this.breadcrumbList.add(breadcrumb);
    }

    /**
     * パンくず情報
     *
     * @version 1.0.0
     */
    public static class Breadcrumb {

        /** 遷移先名 */
        private String viewName;
        /** URL */
        private String url;

        /**
         * viewNameを返す
         *
         * @return viewName
         */
        public String getViewName() {
            return viewName;
        }

        /**
         * viewNameを設定する
         *
         * @param viewName
         *     遷移先名
         */
        public void setViewName(String viewName) {
            this.viewName = viewName;
        }

        /**
         * urlを返す
         *
         * @return url
         */
        public String getUrl() {
            return url;
        }

        /**
         * urlを設定する
         *
         * @param url
         *     URL
         */
        public void setUrl(String url) {
            this.url = url;
        }

    }
}
