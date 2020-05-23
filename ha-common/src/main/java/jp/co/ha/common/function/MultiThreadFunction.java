package jp.co.ha.common.function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import jp.co.ha.common.type.BaseEnum;

/**
 * 並列処理を扱う基底クラス<br>
 * 継承クラスのOverrideした{@linkplain MultiThreadFunction#getThreadCount}メソッドのスレッド数で並列処理を行う<br>
 * 並列処理で行いたい処理は{@linkplain MultiThreadFunction#getConsumer}を実装し、実処理を定義する
 *
 * @param <T>
 *     並列処理に必要な対象クラス
 * @version 1.0.0
 */
public abstract class MultiThreadFunction<T> {

    /** LOG */
    private static final Logger LOG = LoggerFactory.getLogger(MultiThreadFunction.class);

    /**
     * 並列処理を実装する
     *
     * @param t
     *     並列処理に必要な対象クラス
     * @return 処理結果
     * @throws InterruptedException
     *     スレッドへの割り込み処理が発生した場合
     */
    public final ResultType execute(T t) throws InterruptedException {

        LOG.debug("並列処理開始, スレッド数=" + getThreadCount());

        // 処理結果
        ResultType result = ResultType.FAILURE;
        ExecutorService es = Executors.newFixedThreadPool(getThreadCount());

        try {

            for (int i = 0; i < getThreadCount(); i++) {
                es.execute(() -> getConsumer().accept(t));
            }
            result = ResultType.SUCCESS;

        } finally {

            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
            LOG.debug("並列処理終了");

        }

        return result;
    }

    /**
     * 並列処理の実装を返す
     *
     * @return 並列処理の実装
     */
    public abstract Consumer<T> getConsumer();

    /**
     * スレッド数を返す
     *
     * @return スレッド数
     */
    public abstract int getThreadCount();

    /**
     * 処理結果の列挙
     *
     * @version 1.0.0
     */
    public static enum ResultType implements BaseEnum {

        /** 正常終了 */
        SUCCESS("0"),
        /** 失敗 */
        FAILURE("1");

        /** 値 */
        private String value;

        /**
         * コンストラクタ
         *
         * @param value
         *     値
         */
        private ResultType(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }
}
