package jp.co.ha.auto;

import org.openqa.selenium.WebDriver;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import junit.framework.TestCase;

/**
 * 自動テストの基底クラス<br>
 * 画面テストはchromeのバージョンは77で行うことを想定している
 *
 * @since 1.0
 */
public abstract class BaseAutoTest extends TestCase {

	/** LOG */
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
	/** 基底URL */
	protected final String BASE_URL = "http://localhost:8080";
	/** WebDriver */
	protected WebDriver driver = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setUp() throws Exception {
		LOG.info("#setUp");
		AutoTestConfig conf = new AutoTestCommonExecuter().execute();
		this.driver = conf.getDriver();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void tearDown() throws Exception {
		LOG.info("#tearDown");
		// driver.quit();
	}

}
