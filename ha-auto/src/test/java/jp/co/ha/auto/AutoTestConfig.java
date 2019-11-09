package jp.co.ha.auto;

import org.openqa.selenium.WebDriver;

/**
 * 自動テストの設定情報クラス
 *
 * @since 1.0
 *
 */
public class AutoTestConfig {

	/** WebDriver */
	private WebDriver driver;

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
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

}
