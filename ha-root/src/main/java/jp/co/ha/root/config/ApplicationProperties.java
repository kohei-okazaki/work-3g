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

    /**
     * frontUrlを返す
     *
     * @return frontUrl
     */
    public String getFrontUrl() {
        return frontUrl;
    }

    /**
     * frontUrlを設定する
     *
     * @param frontUrl
     *     Front画面URL
     */
    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    /**
     * newsPageを返す
     *
     * @return newsPage
     */
    public int getNewsPage() {
        return newsPage;
    }

    /**
     * newsPageを設定する
     *
     * @param newsPage
     */
    public void setNewsPage(int newsPage) {
        this.newsPage = newsPage;
    }

    /**
     * notePageを返す
     *
     * @return notePage
     */
    public int getNotePage() {
        return notePage;
    }

    /**
     * notePageを設定する
     *
     * @param notePage
     */
    public void setNotePage(int notePage) {
        this.notePage = notePage;
    }

    /**
     * accountPageを返す
     *
     * @return accountPage
     */
    public int getAccountPage() {
        return accountPage;
    }

    /**
     * accountPageを設定する
     *
     * @param accountPage
     */
    public void setAccountPage(int accountPage) {
        this.accountPage = accountPage;
    }

    /**
     * apiPageを返す
     *
     * @return apiPage
     */
    public int getApiPage() {
        return apiPage;
    }

    /**
     * apiPageを設定する
     *
     * @param apiPage
     */
    public void setApiPage(int apiPage) {
        this.apiPage = apiPage;
    }

    /**
     * healthinfoPageを返す
     *
     * @return healthinfoPage
     */
    public int getHealthinfoPage() {
        return healthinfoPage;
    }

    /**
     * healthinfoPageを設定する
     *
     * @param healthinfoPage
     */
    public void setHealthinfoPage(int healthinfoPage) {
        this.healthinfoPage = healthinfoPage;
    }

}
