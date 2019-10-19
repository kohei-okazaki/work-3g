package jo.co.ha.auto;

import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import jp.co.ha.common.exception.BaseException;
import jp.co.ha.common.exception.CommonErrorCode;
import jp.co.ha.common.exception.SystemException;
import jp.co.ha.common.io.file.property.reader.PropertyReader;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;

/**
 * 自動テスト実行時の共通処理をまとめたクラス
 *
 */
public class AutoTestCommonExecuter {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());

	public AutoTestConfig execute() throws SystemException {

		AutoProperties prop = read().orElseThrow(
				() -> new SystemException(CommonErrorCode.FILE_READING_ERROR, "auto.propertiesの読み込みに失敗しました"));

		AutoTestConfig conf = new AutoTestConfig();
		switch (BrowserType.of(prop.getBrowser())) {
		case IE:
			System.setProperty("webdriver.chrome.driver", prop.getIeDriverPath());
			conf.setDriver(new InternetExplorerDriver());
			break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver", prop.getChromeDriverPath());
			conf.setDriver(new ChromeDriver());
			break;
		case FIRE_FOX:
			System.setProperty("webdriver.chrome.driver", prop.getFireFoxDriverPath());
			conf.setDriver(new FirefoxDriver());
			break;
		case SAFARI:
			System.setProperty("webdriver.chrome.driver", prop.getFireFoxDriverPath());
			conf.setDriver(new SafariDriver());
			break;
		}

		return conf;
	}

	private Optional<AutoProperties> read() {
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			AutoProperties prop = new PropertyReader().read(path, "auto.properties", AutoProperties.class);
			return Optional.ofNullable(prop);
		} catch (BaseException e) {
			LOG.error("auto.propertiesの読み込みに失敗しました", e);
			return Optional.empty();
		}
	}

	/**
	 * ブラウザ種別の列挙
	 *
	 */
	public static enum BrowserType implements BaseEnum {

		/** IE */
		IE("Internet Explore"),
		/** CHROME */
		CHROME("google chrome"),
		/** FIRE_FOX */
		FIRE_FOX("fire fox"),
		/** SAFARI */
		SAFARI("safari");

		private String value;

		private BrowserType(String value) {
			this.value = value;
		}

		@Override
		public String getValue() {
			return this.value;
		}

		public static BrowserType of(String value) {
			return BaseEnum.of(BrowserType.class, value);
		}
	}

}
