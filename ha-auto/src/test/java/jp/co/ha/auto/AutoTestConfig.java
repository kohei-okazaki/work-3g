package jp.co.ha.auto;

import org.openqa.selenium.WebDriver;

/**
 * 自動テストの設定情報クラス
 *
 * @version 1.0.0
 */
public class AutoTestConfig {

    /** WebDriver */
    private WebDriver driver;
    /** ブラウザの基底パス */
    private String baseUrl;

    /**
     * driverを返す
     *
     * @return driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * driverを設定する
     *
     * @param driver
     *     WebDriver
     */
    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * baseUrlを返す
     *
     * @return baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * baseUrlを設定する
     *
     * @param baseUrl
     *     ブラウザの基底パス
     */
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

}
