package jp.co.ha.auto;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import junit.framework.TestCase;

/**
 * 自動テストの基底クラス<br>
 * 画面テストはchromeのバージョンは79で行うことを想定している
 *
 * @version 1.0.0
 */
public abstract class BaseAutoTest extends TestCase {

    /** LOG */
    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    /** 自動テストの設定ファイル保持情報 */
    protected AutoTestConfig conf;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUp() throws Exception {
        LOG.info("#setUp");
        this.conf = new AutoTestCommonExecuter().execute();
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
