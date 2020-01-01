package jp.co.ha.tool;

import jp.co.ha.tool.build.BuildInvoker;

/**
 * 処理クラス
 *
 * @since 1.0
 */
public class ToolExecutor {

	/**
	 * 実行したいビルダークラスをjp.co.ha.tool.build.BuildInvoker#buildの引数に設定して下さい
	 * <ul>
	 * <li>AddColumnBuilder</li>
	 * <li>CreateTableBuilder</li>
	 * <li>DropBuilder</li>
	 * <li>EntityBuilder</li>
	 * <li>TableDefineBuilder</li>
	 * </ul>
	 *
	 * @param args
	 *     使わない
	 */
	public static void main(String[] args) {
		BuildInvoker.build("CreateTableBuilder", "TableDefineBuilder", "DropBuilder");
	}
}
