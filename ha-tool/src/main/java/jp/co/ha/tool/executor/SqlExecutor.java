package jp.co.ha.tool.executor;

import jp.co.ha.tool.build.DdlBuilder;
import jp.co.ha.tool.build.DropBuilder;
import jp.co.ha.tool.build.EntityBuilder;

public class SqlExecutor {

	public static void main(String[] args) {
		// DDLを作成
		DdlBuilder ddlBuilder = new DdlBuilder();
		ddlBuilder.execute();

		// DropSqlを作成
		DropBuilder dropBuilder = new DropBuilder();
		dropBuilder.execute();

		// Entityを作成
		EntityBuilder entityBuilder = new EntityBuilder();
		entityBuilder.execute();
	}
}
