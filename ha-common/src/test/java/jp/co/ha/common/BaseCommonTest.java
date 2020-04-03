package jp.co.ha.common;

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
 * commonの基底テストクラス
 *
 * @version 1.0.0
 */
@ContextConfiguration(locations = { "classpath:common-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseCommonTest {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BaseCommonTest.class);

    /**
     * before処理
     */
    @Before
    public void before() {
        LOG.debug("BaseCommonTest#before");
        BeanLoader.setContext(new ClassPathXmlApplicationContext(
                new String[] { "classpath:common-context.xml" }));
    }

    /**
     * after処理
     */
    @After
    public void after() {
        LOG.debug("BaseCommonTest#after");
    }

}
