package jp.co.ha.tool.build;

import java.util.Objects;
import java.util.StringJoiner;

import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.reader.ExcelReader;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ExecuteType;

public class DdlBuilder extends CommonBuilder {

	@Override
	public void execute() {

		ExcelReader reader = new ExcelReader(getExcelConfig());

		for (String table : this.tableList) {
			StringJoiner sb = new StringJoiner("\r\n");
			String ddlBegin = "CREATE TABLE " + table + " (";
			String ddlEnd = ");";
			sb.add(ddlBegin);
			Excel excel = reader.read();
			excel.activeSheet("TABLE_LIST");
			StringJoiner rowValue = new StringJoiner(",\r\n");
			for (Row row : excel.getRowList()) {
				if (isTargetTable(row, table)) {
					// カラム名を取得
					String columnName = getColumnName(row);
					// カラム定義を取得
					String columnType = getColumnType(row);
					rowValue.add(columnName + " " + columnType);
				}
			}
			sb.add(rowValue.toString());
			sb.add(ddlEnd);

			FileConfig fileConf = getFileConfig(ExecuteType.DDL);
			fileConf.setFileName(table.toUpperCase() + ".sql");
			fileConf.setData(sb.toString());
			new FileFactory().create(fileConf);
		}
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
