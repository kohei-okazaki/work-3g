package jp.co.ha.db;

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
 * 基底dbテストクラス
 *
 */
@ContextConfiguration(locations = { "classpath:common-context.xml", "classpath:db-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseDbTest {

	/** LOG */
	private static final Logger LOG = LoggerFactory.getLogger(BaseDbTest.class);

	/**
	 * before
	 */
	@Before
	public void before() {
		LOG.debug("BaseDbTest#before");
		BeanLoader.setContext(new ClassPathXmlApplicationContext(
				new String[] { "classpath:common-context.xml", "classpath:db-context.xml" }));
	}

	/**
	 * after
	 */
	@After
	public void after() {
		LOG.debug("BaseDbTest#after");
	}
}
