package jp.co.ha.tool.build;

import java.util.Objects;
import java.util.StringJoiner;

import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;

public class DdlBuilder extends CommonBuilder {

	@Override
	public void execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		for (String table : this.targetTableList) {
			StringJoiner body = new StringJoiner("\r\n");
			String ddlPrefix = "CREATE TABLE " + table + " (";
			String ddlSuffix = ");";
			body.add(ddlPrefix);
			StringJoiner rowValue = new StringJoiner(",\r\n");
			for (Row row : excel.getRowList()) {
				if (isTargetTable(row, table)) {
					// コメントを取得
					String columnComment = getColumnComment(row);
					// カラム名を取得
					String columnName = getColumnName(row);
					// カラム定義を取得
					String columnType = getColumnType(row);
					rowValue.add(columnComment + "\r\n" + columnName + " " + columnType);
				}
			}
			body.add(rowValue.toString()).add(ddlSuffix);

			FileConfig fileConf = getFileConfig(ExecuteType.DDL);
			fileConf.setFileName(table.toUpperCase() + ".sql");
			fileConf.setData(body.toString());
			new FileFactory().create(fileConf);
		}
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
		// カラム定義とサイズを取得
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
