package jp.co.ha.tool.build;

import java.util.Objects;
import java.util.StringJoiner;

import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.reader.ExcelReader;
import jp.co.ha.tool.type.CellPositionType;

public class DdlBuilder extends BaseBuilder {

	public void execute() {

		ExcelConfig excelConf = new ExcelConfig();
		excelConf.setFilePath("META-INF\\DB.xlsx");
		excelConf.setSheetName("TABLE_LIST");
		ExcelReader reader = new ExcelReader(excelConf);

		for (String table : this.tableList) {
			StringJoiner sb = new StringJoiner("\r\n");
			String ddlBegin = "CREATE TABLE " + table + " (";
			String ddlEnd = ");";
			sb.add(ddlBegin);
			Excel excel = reader.read();
			excel.activeSheet("TABLE_LIST");
			StringJoiner rowValue = new StringJoiner(",\r\n\r\n");
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

			FileConfig fileConf = new FileConfig();
			fileConf.setOutputPath(super.baseDir + "\\ha-resource\\db\\ddl");
			fileConf.setFileName(table.toUpperCase() + ".sql");
			fileConf.setData(sb.toString());
			new FileFactory().create(fileConf);
		}

	}

	private String getColumnName(Row row) {
		return row.getCell(CellPositionType.COLUMN_NAME).getValue();
	}

	private String getColumnType(Row row) {
		StringJoiner sj = new StringJoiner(" ");
		// カラム定義とサイズを取得
		String columnType = row.getCell(CellPositionType.COLUMN_TYPE).getValue();
		String size = getSize(row);
		sj.add(columnType + size);
		if (isSequence(row)) {
			sj.add("AUTO_INCREMENT");
		}
		if (isPrimaryKey(row)) {
			sj.add("NOT NULL PRIMARY KEY");
		}

		return sj.toString();
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

	private boolean isTargetTable(Row row, String table) {
		Cell cell = row.getCell(CellPositionType.TABLE_NAME);
		return table.equals(cell.getValue());
	}}
