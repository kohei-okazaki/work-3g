package jp.co.ha.batch.invoke;

/**
 * Batchのエントリー部分クラス<br>
 * 本クラスからBatchInvokerを起動する
 *
 * @version 1.0.0
 */
public class BatchEntry {

    /**
     * 起動メソッド
     *
     * @param args
     *     String[]
     */
    public static void main(String[] args) {
        BatchInvoker.invoke(args);
    }

}
