package jp.co.ha.tool.build;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.FileUtil.LineFeedType;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.build.annotation.Build;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.config.FileConfig.FileConfigBuilder;
import jp.co.ha.tool.db.Column;
import jp.co.ha.tool.db.Table;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;

/**
 * テーブル作成のDDLを作成するビルダー
 *
 * @since 1.0
 */
public class CreateTableBuilder extends BaseSqlSourceBuilder {

	/**
	 * 実処理
	 *
	 * @return ファイル設定情報
	 */
	@Build
	public List<FileConfig> execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		List<FileConfig> list = new ArrayList<>();
		for (String tableName : this.targetTableList) {

			Table table = toTable(excel.getRowList(), tableName);
			StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
			body.add(getTableComment(table.getLogicalName()));
			body.add("CREATE TABLE " + tableName + " (");
			StringJoiner columnData = new StringJoiner(StringUtil.COMMA + LineFeedType.CRLF.getValue());

			table.getColumnList().stream().forEach(e -> {

				String comment = e.getComment();
				String name = e.getName();
				String type = e.getType();

				StringBuilder sb = new StringBuilder();
				sb.append("-- ");
				sb.append(comment);
				sb.append(StringUtil.NEW_LINE);
				sb.append(name);
				sb.append(StringUtil.SPACE);
				sb.append(type);
				sb.append(" comment '");
				sb.append(comment);
				sb.append("'");

				columnData.add(sb.toString());
			});
			body.add(columnData.toString());
			body.add(");");

			String outputPath = getOutputPath(ExecuteType.DDL);
			String fileName = tableName.toUpperCase() + FileExtension.SQL.getValue();

			list.add(new FileConfigBuilder(outputPath, fileName, body.toString()).build());
		}
		return list;
	}

	/**
	 * Tableを返す
	 *
	 * @param rowList
	 *     行リスト
	 * @param tableName
	 *     テーブル名
	 * @return Tableインスタンス
	 */
	private Table toTable(List<Row> rowList, String tableName) {

		Table table = new Table();
		table.setPhysicalName(tableName);
		String logicalName = rowList.stream().filter(e -> isTargetTable(e, tableName))
				.map(e -> e.getCell(CellPositionType.LOGICAL_NAME)).collect(Collectors.toList()).get(0).getValue();
		table.setLogicalName(logicalName);
		table.setColumnList(rowList.stream().filter(e -> isTargetTable(e, tableName)).map(e -> {
			Column column = new Column();
			column.setName(getColumnName(e));
			column.setComment(getColumnComment(e));
			column.setType(getColumnType(e));
			return column;
		}).collect(Collectors.toList()));
		return table;
	}

}
