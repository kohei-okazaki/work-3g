package jp.co.ha.batch;

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
 * Batch基底テストクラス
 *
 * @version 1.0.0
 */
@ContextConfiguration(locations = { "classpath:common-context.xml",
        "classpath:db-context.xml", "classpath:web-context.xml",
        "classpath:business-context.xml", "classpath:batch-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseBatchTest {

    /** LOG */
    protected static final Logger LOG = LoggerFactory.getLogger(BaseBatchTest.class);

    /**
     * before
     */
    @Before
    public void before() {
        LOG.debug("BaseBatchTest#before");
        BeanLoader.setContext(new ClassPathXmlApplicationContext(
                new String[] { "classpath:common-context.xml", "classpath:db-context.xml",
                        "classpath:web-context.xml", "classpath:business-context.xml",
                        "classpath:batch.xml" }));
    }

    /**
     * after
     */
    @After
    public void after() {
        LOG.debug("BaseBatchTest#after");
    }

}
