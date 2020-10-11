package jp.co.ha.common.function;

import static org.junit.Assert.*;

import java.util.function.Consumer;

import org.junit.Test;

import jp.co.ha.common.BaseCommonTest;
import jp.co.ha.common.function.MultiThreadFunction.ResultType;
import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;

/**
 * {@linkplain MultiThreadFunction} のjUnit
 *
 * @version 1.0.0
 */
public class MultiThreadFunctionTest extends BaseCommonTest {

    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(MultiThreadFunctionTest.class);

    /**
     * {@linkplain MultiThreadFunction#execute(Object)} のjUnit
     */
    @Test
    public void testExecute() {

        try {

            String s = "VALUE=";
            StringPrint sp = new StringPrint();
            ResultType result = sp.execute(s);
            assertEquals(ResultType.SUCCESS, result);

        } catch (InterruptedException e) {
            LOG.error("割り込み処理が発生しました", e);
        }
    }

    /**
     * {@linkplain MultiThreadFunction}のテスト用の継承クラス
     *
     * @version 1.0.0
     */
    private static class StringPrint extends MultiThreadFunction<String> {

        @Override
        public Consumer<String> getConsumer() {

            return (s) -> {

                for (int i = 0; i < 10; i++) {
                    if (i % 5 == 0 && i % 3 == 0) {
                        LOG.info(s + Thread.currentThread().getName() + ", No=" + i
                                + ", fizzbazz");
                    } else if (i % 5 == 0) {
                        LOG.info(s + Thread.currentThread().getName() + ", No=" + i
                                + ", bazz");
                    } else if (i % 3 == 0) {
                        LOG.info(s + Thread.currentThread().getName() + ", No=" + i
                                + ", fizz");
                    }
                }
            };
        }

        @Override
        public int getThreadCount() {
            return 2;
        }

    }
}
