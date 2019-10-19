package jo.co.ha.auto;

import org.openqa.selenium.WebDriver;

public class AutoTestConfig {

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
