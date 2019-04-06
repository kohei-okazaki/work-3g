package jp.co.ha.batch.invoke;

/**
 * Batchのエントリー部分クラス<br>
 * 本クラスからBatchInvokerを起動する
 *
 */
public class BatchEntry {

	public static void main(String[] args) {
		BatchInvoker.invoke(args);
	}

}
