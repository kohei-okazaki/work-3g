package jp.co.ha.auto;

import jp.co.ha.common.io.file.property.annotation.Property;

/**
 * auto.proertiesのBean
 * 
 * @since 1.0
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
	 */
	public void setFireFoxDriverPath(String fireFoxDriverPath) {
		this.fireFoxDriverPath = fireFoxDriverPath;
	}

}
