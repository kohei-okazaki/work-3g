package jp.co.ha.tool.build;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.build.annotation.Build;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.db.Column;
import jp.co.ha.tool.db.Table;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;

/**
 * テーブル作成のDDLを作成するビルダー
 *
 */
public class CreateTableBuilder extends BaseBuilder {

	@Build
	public void execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		for (String tableName : this.targetTableList) {
			StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
			body.add("CREATE TABLE " + tableName + " (");
			Table table = toTable(excel.getRowList(), tableName);
			StringJoiner columnData = new StringJoiner(StringUtil.COMMA + StringUtil.CRLF);
			table.getColumnList().stream().forEach(e -> {
				String comment = e.getComment();
				String name = e.getName();
				String type = e.getType();
				columnData.add(comment + StringUtil.NEW_LINE + name + StringUtil.SPACE + type);
			});
			body.add(columnData.toString());
			body.add(");");

			FileConfig conf = getFileConfig(ExecuteType.DDL);
			conf.setFileName(tableName.toUpperCase() + FileExtension.SQL.getValue());
			conf.setData(body.toString());
			FileFactory.create(conf);
		}
	}

	private Table toTable(List<Row> rowList, String tableName) {
		Table table = new Table();
		table.setPhysicalName(tableName);
		table.setColumnList(rowList.stream().filter(e -> isTargetTable(e, tableName)).map(e -> {
			Column column = new Column();
			column.setName(getColumnName(e));
			column.setComment(getColumnComment(e));
			column.setType(getColumnType(e));
			return column;
		}).collect(Collectors.toList()));
		return table;
	}

	private String getColumnComment(Row row) {
		return "-- " + row.getCell(CellPositionType.COLUMN_NAME_COMMENT).getValue();
	}

	private String getColumnName(Row row) {
		return row.getCell(CellPositionType.COLUMN_NAME).getValue();
	}

	private String getColumnType(Row row) {
		StringJoiner body = new StringJoiner(StringUtil.SPACE);
		String columnType = row.getCell(CellPositionType.COLUMN_TYPE).getValue();
		String size = getSize(row);
		body.add(columnType + size);
		if (isSequence(row)) {
			body.add("AUTO_INCREMENT");
		}
		if (isPrimaryKey(row)) {
			body.add("NOT NULL PRIMARY KEY");
		}
		return body.toString();
	}

	private String getSize(Row row) {
		String size = row.getCell(CellPositionType.COLUMN_SIZE).getValue();
		return StringUtil.isBrank(size) ? StringUtil.EMPTY : "(" + size + ")";
	}

	private boolean isSequence(Row row) {
		Predicate<Row> function = e -> StringUtil.isTrue(e.getCell(CellPositionType.SEQUENCE).getValue());
		return function.test(row);
	}

	private boolean isPrimaryKey(Row row) {
		Predicate<Row> function = e -> StringUtil.isTrue(e.getCell(CellPositionType.PRIMARY_KEY).getValue());
		return function.test(row);
	}

}
