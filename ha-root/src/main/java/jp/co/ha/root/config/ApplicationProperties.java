package jp.co.ha.root.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 設定ファイル情報<br>
 * <ul>
 * <li>application.yml</li>
 * <li>application-${environment}.yml</li>
 * </ul>
 * のBean定義
 *
 * @version 1.0.0
 */
@Component
public class ApplicationProperties {

    /** Front画面URL */
    @Value("${front.url}")
    private String frontUrl;

    /** 1ページあたりの件数 */
    @Value("${front.news.page}")
    private int newsPage;

    /** 1ページあたりの件数 */
    @Value("${front.note.page}")
    private int notePage;

    /** 1ページあたりの件数 */
    @Value("${front.account.page}")
    private int accountPage;

    /** 1ページあたりの件数 */
    @Value("${front.api.page}")
    private int apiPage;

    /** 1ページあたりの件数 */
    @Value("${front.healthinfo.page}")
    private int healthinfoPage;

    /** 1ページあたりの件数 */
    @Value("${front.inquiry.page}")
    private int inquiryPage;

    /**
     * Front画面URLを返す
     *
     * @return frontUrl
     */
    public String getFrontUrl() {
        return frontUrl;
    }

    /**
     * Front画面URLを設定する
     *
     * @param frontUrl
     *     Front画面URL
     */
    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    /**
     * 1ページあたりの件数を返す
     *
     * @return newsPage
     */
    public int getNewsPage() {
        return newsPage;
    }

    /**
     * 1ページあたりの件数を設定する
     *
     * @param newsPage
     *     1ページあたりの件数
     */
    public void setNewsPage(int newsPage) {
        this.newsPage = newsPage;
    }

    /**
     * 1ページあたりの件数を返す
     *
     * @return notePage
     */
    public int getNotePage() {
        return notePage;
    }

    /**
     * 1ページあたりの件数を設定する
     *
     * @param notePage
     *     1ページあたりの件数
     */
    public void setNotePage(int notePage) {
        this.notePage = notePage;
    }

    /**
     * 1ページあたりの件数を返す
     *
     * @return accountPage
     */
    public int getAccountPage() {
        return accountPage;
    }

    /**
     * 1ページあたりの件数を設定する
     *
     * @param accountPage
     *     1ページあたりの件数
     */
    public void setAccountPage(int accountPage) {
        this.accountPage = accountPage;
    }

    /**
     * 1ページあたりの件数を返す
     *
     * @return apiPage
     */
    public int getApiPage() {
        return apiPage;
    }

    /**
     * 1ページあたりの件数を設定する
     *
     * @param apiPage
     *     1ページあたりの件数
     */
    public void setApiPage(int apiPage) {
        this.apiPage = apiPage;
    }

    /**
     * 1ページあたりの件数を返す
     *
     * @return healthinfoPage
     */
    public int getHealthinfoPage() {
        return healthinfoPage;
    }

    /**
     * 1ページあたりの件数を設定する
     *
     * @param healthinfoPage
     *     1ページあたりの件数
     */
    public void setHealthinfoPage(int healthinfoPage) {
        this.healthinfoPage = healthinfoPage;
    }

    /**
     * 1ページあたりの件数を返す
     *
     * @return inquiryPage
     */
    public int getInquiryPage() {
        return inquiryPage;
    }

    /**
     * 1ページあたりの件数を設定する
     *
     * @param inquiryPage
     *     1ページあたりの件数
     */
    public void setInquiryPage(int inquiryPage) {
        this.inquiryPage = inquiryPage;
    }

}
