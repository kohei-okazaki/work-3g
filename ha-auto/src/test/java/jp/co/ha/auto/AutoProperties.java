package jp.co.ha.auto;

import jp.co.ha.common.io.file.property.annotation.Property;

/**
 * auto.proertiesのBean
 *
 * @version 1.0.0
 */
public class AutoProperties {

    /** ブラウザ種別 */
    @Property(name = "browser")
    private String browser;
    /** IE driverのパス */
    @Property(name = "ieDriverPath")
    private String ieDriverPath;
    /** Chrome driverのパス */
    @Property(name = "chromeDriverPath")
    private String chromeDriverPath;
    /** Firefox driverのパス */
    @Property(name = "fireFoxDriverPath")
    private String fireFoxDriverPath;
    /** ブラウザの基底パス */
    @Property(name = "baseUrl")
    private String baseUrl;

    /**
     * browserを返す
     *
     * @return browser
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * browserを設定する
     *
     * @param browser
     *     ブラウザ種別
     */
    public void setBrowser(String browser) {
        this.browser = browser;
    }

    /**
     * ieDriverPathを返す
     *
     * @return ieDriverPath
     */
    public String getIeDriverPath() {
        return ieDriverPath;
    }

    /**
     * ieDriverPathを設定する
     *
     * @param ieDriverPath
     *     IE driverのパス
     */
    public void setIeDriverPath(String ieDriverPath) {
        this.ieDriverPath = ieDriverPath;
    }

    /**
     * chromeDriverPathを返す
     *
     * @return chromeDriverPath
     */
    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    /**
     * chromeDriverPathを設定する
     *
     * @param chromeDriverPath
     *     Chrome driverのパス
     */
    public void setChromeDriverPath(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;
    }

    /**
     * fireFoxDriverPathを返す
     *
     * @return fireFoxDriverPath
     */
    public String getFireFoxDriverPath() {
        return fireFoxDriverPath;
    }

    /**
     * fireFoxDriverPathを設定する
     *
     * @param fireFoxDriverPath
     *     Firefox driverのパス
     */
    public void setFireFoxDriverPath(String fireFoxDriverPath) {
        this.fireFoxDriverPath = fireFoxDriverPath;
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
