package jp.co.ha.batch.execute;

import org.junit.Test;

import jp.co.ha.batch.BaseBatchTest;
import jp.co.ha.batch.invoke.BatchInvoker;

/**
 * {@linkplain HealthInfoFileRegistBatch}のjUnit
 *
 * @version 1.0.0
 */
public class HealthInfoFileRegistBatchTest extends BaseBatchTest {

    /**
     * 正常系のテスト
     */
    @Test
    public void test() {
        String[] args = new String[] { "HealthInfoFileRegistBatch" };
        BatchInvoker.invoke(args);
    }

}
