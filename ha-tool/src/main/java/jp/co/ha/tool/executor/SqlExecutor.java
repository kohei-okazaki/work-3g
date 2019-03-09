package jp.co.ha.tool.executor;

import jp.co.ha.tool.build.BuildInvoker;

/**
 * 処理クラス
 *
 */
public class SqlExecutor {

	/**
	 * 実行したいビルダークラスをjp.co.ha.tool.build.BuildInvoker#buildの引数に設定して下さい
	 *
	 * @param args
	 *     使わない
	 */
	public static void main(String[] args) {
		BuildInvoker.build("AddColumnBuilder", "CreateTableBuilder");
	}
}
