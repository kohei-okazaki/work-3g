package jp.co.ha.common.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

@ContextConfiguration(locations = { "classpath:common-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseCommonTest {

	private static final Logger LOG = LoggerFactory.getLogger(BaseCommonTest.class);

	@Before
	public void before() {
		LOG.debug("BaseCommonTest#before");
	}

	@After
	public void after() {
		LOG.debug("BaseCommonTest#after");
	}
}
