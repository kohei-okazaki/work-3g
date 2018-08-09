package jp.co.ha.tool.build;

import java.util.StringJoiner;

import org.apache.poi.ss.usermodel.Row;

import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.read.ExcelReader;

public class DdlBuilder extends BaseBuilder {

	public void execute() {

		ExcelConfig excelConf = new ExcelConfig();
		excelConf.setFilePath("META-INF/DB.xlsx");
		excelConf.setSheetName("TABLE_LIST");
		ExcelReader reader = new ExcelReader(excelConf);

		for (String table : this.tableList) {

			StringJoiner sb = new StringJoiner("\r\n");
			String ddlBegin = "CREATE TABLE " + table + " (";
			String ddlEnd = ");";

			sb.add(ddlBegin);
			reader.read();
			StringJoiner rowValue = new StringJoiner(",\r\n\r\n");
			while (reader.hasRow()) {
				Row row = reader.readRow();
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
		return row.getCell(5).getStringCellValue();
	}

	private String getColumnType(Row row) {
		StringJoiner sj = new StringJoiner(" ");
		// カラム定義とサイズを取得
		String columnTypeAndSize = row.getCell(6).getStringCellValue();
		sj.add(columnTypeAndSize);
		if (isSequence(row)) {
			sj.add("AUTO_INCREMENT");
		}
		if (isPrimaryKey(row)) {
			sj.add("NOT NULL PRIMARY KEY");
		}

		return sj.toString();
	}

	private boolean isSequence(Row row) {
		return "1".equals(row.getCell(3).getStringCellValue());
	}

	private boolean isPrimaryKey(Row row) {
		return "1".equals(row.getCell(2).getStringCellValue());
	}

	private boolean isTargetTable(Row row, String table) {
		String excelTableName = row.getCell(1).getStringCellValue();
		return table.equals(excelTableName);
	}}
