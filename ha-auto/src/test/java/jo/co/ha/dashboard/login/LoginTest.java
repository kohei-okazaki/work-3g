package jo.co.ha.dashboard.login;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

	@Test
	public void test() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver",
				"C:\\app\\git\\work-3g\\ha-auto\\bin\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("http://localhost:8080/ha-dashboard/login/index");
		WebElement loginIdElement = driver.findElement(By.id("userId"));
		loginIdElement.sendKeys("master");
		WebElement passwordElement = driver.findElement(By.id("password"));
		passwordElement.sendKeys("master");
		WebElement submitButton = driver.findElement(By.id("submit"));
		submitButton.click();
		Thread.sleep(10000);

		// これをやると画面が消える
		driver.quit();
	}
}
