package jp.co.ha.tool.executor;

import jp.co.ha.tool.build.DdlBuilder;
import jp.co.ha.tool.build.EntityBuilder;

public class SqlExecutor {

	public static void main(String[] args) {
		DdlBuilder ddlBuilder = new DdlBuilder();
		// excelファイルからDDLを作成
		ddlBuilder.execute();
		// DDLをファイルに書き出す
//		ddlBuilder.comit();

		EntityBuilder entityBuilder = new EntityBuilder();


	}
}
