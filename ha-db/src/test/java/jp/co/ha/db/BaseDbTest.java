package jp.co.ha.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * 基底dbテストクラス
 *
 * @version 1.0.0
 */
@ExtendWith(SpringExtension.class)
public class BaseDbTest {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(BaseDbTest.class);

    /**
     * before
     */
    @BeforeEach
    public void before() {
        LOG.debug("BaseDbTest#before");
    }

    /**
     * after
     */
    @AfterEach
    public void after() {
        LOG.debug("BaseDbTest#after");
    }
}
