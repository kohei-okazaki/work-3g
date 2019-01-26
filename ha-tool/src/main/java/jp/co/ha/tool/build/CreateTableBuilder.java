package jp.co.ha.tool.build;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.util.FileUtil.FileExtension;
import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.db.Column;
import jp.co.ha.tool.db.Table;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;

/**
 * テーブルを作成するビルダー
 *
 */
public class CreateTableBuilder extends CommonBuilder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		for (String tableName : this.targetTableList) {
			StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
			String ddlPrefix = "CREATE TABLE " + tableName + " (";
			String ddlSuffix = ");";
			body.add(ddlPrefix);
			Table table = getTable(excel.getRowList(), tableName);
			StringJoiner columnData = new StringJoiner(StringUtil.COMMA + StringUtil.CRLF);
			for (Column column : table.getColumnList()) {
				columnData.add(column.getComment() + StringUtil.NEW_LINE + column.getName() + StringUtil.SPACE + column.getType());
			}
			body.add(columnData.toString()).add(ddlSuffix);

			FileConfig fileConf = getFileConfig(ExecuteType.DDL);
			fileConf.setFileName(tableName.toUpperCase() + FileExtension.SQL.getValue());
			fileConf.setData(body.toString());
			FileFactory.create(fileConf);
		}
	}

	private Table getTable(List<Row> rowList, String tableName) {
		Table table = new Table();
		table.setPhysicalName(tableName);
		List<Column> columnList = new ArrayList<>();
		rowList.stream().filter(e -> isTargetTable(e, tableName)).forEach(e -> {
			Column column = new Column();
			column.setName(getColumnName(e));
			column.setComment(getColumnComment(e));
			column.setType(getColumnType(e));
			columnList.add(column);
			table.setColumnList(columnList);
		});
		return table;
	}

	private String getColumnComment(Row row) {
		String comment = row.getCell(CellPositionType.COLUMN_NAME_COMMENT).getValue();
		return "-- " + comment;
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
		return StringUtil.isTrue(row.getCell(CellPositionType.SEQUENCE).getValue());
	}

	private boolean isPrimaryKey(Row row) {
		return StringUtil.isTrue(row.getCell(CellPositionType.PRIMARY_KEY).getValue());
	}

}
