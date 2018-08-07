package jp.co.ha.tool.executor;

import jp.co.ha.tool.build.DdlBuilder;

public class SqlExecutor {

	public static void main(String[] args) {
		DdlBuilder builder = new DdlBuilder();
		builder.execute();


	}
}
