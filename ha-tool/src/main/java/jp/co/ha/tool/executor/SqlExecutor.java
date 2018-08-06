package jp.co.ha.tool.executor;

import jp.co.ha.tool.build.SqlBuilder;

public class SqlExecutor {

	public static void main(String[] args) {
		SqlBuilder builder = new SqlBuilder();
		builder.execute();
	}
}
