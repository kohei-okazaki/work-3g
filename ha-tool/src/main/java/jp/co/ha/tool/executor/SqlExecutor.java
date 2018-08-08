package jp.co.ha.tool.executor;

import jp.co.ha.tool.build.DdlBuilder;
import jp.co.ha.tool.build.EntityBuilder;

public class SqlExecutor {

	public static void main(String[] args) {
		// excelファイルからDDLを作成
		DdlBuilder ddlBuilder = new DdlBuilder();
		ddlBuilder.execute();

		// Entityを作成
		EntityBuilder entityBuilder = new EntityBuilder();
		entityBuilder.execute();




	}
}
