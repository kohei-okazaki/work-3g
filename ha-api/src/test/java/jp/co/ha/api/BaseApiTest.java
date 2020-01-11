package jp.co.ha.api;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.system.BeanLoader;

/**
 * API基底テストクラス
 *
 * @since 1.0
 */
@ContextConfiguration(locations = { "classpath:common-context.xml", "classpath:db-context.xml",
		"classpath:web-context.xml", "classpath:business-context.xml", "classpath:api-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseApiTest {

	/** LOG */
	protected static final Logger LOG = LoggerFactory.getLogger(BaseApiTest.class);

	/**
	 * before
	 */
	@Before
	public void before() {
		LOG.debug("BaseApiTest#before");
		BeanLoader.setContext(new ClassPathXmlApplicationContext(
				new String[] { "classpath:common-context.xml", "classpath:db-context.xml", "classpath:web-context.xml",
						"classpath:business-context.xml", "classpath:api-context.xml" }));
	}

	/**
	 * after
	 */
	@After
	public void after() {
		LOG.debug("BaseApiTest#after");
	}
}
