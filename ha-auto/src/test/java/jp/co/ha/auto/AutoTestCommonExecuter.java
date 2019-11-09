package jp.co.ha.auto;

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
 * @since 1.0
 */
public class AutoTestCommonExecuter {

	/** LOG */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/**
	 * 共通処理を行う
	 *
	 * @return 共通処理設定情報
	 * @throws SystemException
	 *     設定ファイルの読み込みに失敗
	 */
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

	/**
	 * 設定ファイルを読み取る
	 *
	 * @return 設定ファイル情報
	 */
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
	 * @since 1.0
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

		/** 値 */
		private String value;

		/**
		 * コンストラクタ
		 *
		 * @param value
		 *     値
		 */
		private BrowserType(String value) {
			this.value = value;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String getValue() {
			return this.value;
		}

		/**
		 * 指定された値からブラウザ種別の列挙を返す
		 *
		 * @param value
		 *     値
		 * @return ブラウザ種別の列挙
		 */
		public static BrowserType of(String value) {
			return BaseEnum.of(BrowserType.class, value);
		}
	}

}
