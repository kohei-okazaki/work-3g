package jp.co.ha.tool.build;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.db.Column;
import jp.co.ha.tool.db.Table;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;
import jp.co.ha.tool.type.FileType;

public class DdlBuilder extends CommonBuilder {

	@Override
	public void execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		for (String tableName : this.targetTableList) {
			StringJoiner body = new StringJoiner("\r\n");
			String ddlPrefix = "CREATE TABLE " + tableName + " (";
			String ddlSuffix = ");";
			body.add(ddlPrefix);
			Table table = getTable(excel.getRowList(), tableName);
			StringJoiner columnData = new StringJoiner(",\r\n");
			for (Column column : table.getColumnList()) {
				columnData.add(column.getComment() + "\r\n" + column.getName() + " " + column.getType());
			}
			body.add(columnData.toString()).add(ddlSuffix);

			FileConfig fileConf = getFileConfig(ExecuteType.DDL);
			fileConf.setFileName(tableName.toUpperCase() + FileType.SQL.getSuffix());
			fileConf.setData(body.toString());
			FileFactory.create(fileConf);
		}
	}

	private Table getTable(List<Row> rowList, String tableName) {
		Table table = new Table();
		table.setPhysicalName(tableName);
		List<Column> columnList = new ArrayList<>();
		for (Row row : rowList) {
			if (isTargetTable(row, tableName)) {
				Column column = new Column();
				column.setName(getColumnName(row));
				column.setComment(getColumnComment(row));
				column.setType(getColumnType(row));
				columnList.add(column);
				table.setColumnList(columnList);
			}
		}
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
		StringJoiner body = new StringJoiner(" ");
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
		return Objects.isNull(size) || "".equals(size) ? "" : "(" + size + ")";
	}

	private boolean isSequence(Row row) {
		return "1".equals(row.getCell(CellPositionType.SEQUENCE).getValue());
	}

	private boolean isPrimaryKey(Row row) {
		return "1".equals(row.getCell(CellPositionType.PRIMARY_KEY).getValue());
	}

}
