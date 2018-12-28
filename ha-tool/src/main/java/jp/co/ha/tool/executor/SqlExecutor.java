package jp.co.ha.tool.executor;

import jp.co.ha.tool.build.DdlBuilder;

/**
 * 処理クラス
 *
 */
public class SqlExecutor {

	/**
	 * 使用するビルダークラスのコメントアウトを消して実行してください
	 *
	 * @param args
	 *     使わない
	 */
	public static void main(String[] args) {
		// DDLを作成
		DdlBuilder ddlBuilder = new DdlBuilder();
		ddlBuilder.execute();

		// DropSqlを作成
//		DropBuilder dropBuilder = new DropBuilder();
//		dropBuilder.execute();

		// Entityを作成
//		EntityBuilder entityBuilder = new EntityBuilder();
//		entityBuilder.execute();
	}
}
