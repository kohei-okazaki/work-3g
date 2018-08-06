package jp.co.ha.tool.build;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlBuilder {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/** Tableリスト */
	private List<String> tableList = Arrays.asList("HEALTH_INFO");
	/** excelファイル */
	private File excelFile;
	/** DDLリスト */
	private List<String> ddlList;

	public SqlBuilder() {
		init();
	}

	private void init() {
		String sysPath = this.getClass().getClassLoader().getResource("").getPath();
		File propFile = new File(sysPath, "META-INF/target.properties");
		LOG.info("sysPath --> " + sysPath);

		this.excelFile = new File(sysPath, "META-INF/DB.xlsx");
		this.ddlList = new ArrayList<>();
	}

	public void execute() {

		StringJoiner sb = new StringJoiner("\r\n");
		try (Workbook workbook = WorkbookFactory.create(this.excelFile)) {

			Sheet sheet = workbook.getSheet("TABLE_LIST");
			for (String table : this.tableList) {
				String ddlBegin = "CREATE TABLE " + table + " (";
				String ddlEnd = ");";

				sb.add(ddlBegin);

				Iterator<Row> iterator = sheet.rowIterator();

				StringJoiner rowValue = new StringJoiner(",\r\n");
				while (iterator.hasNext()) {
					Row row = iterator.next();
					if (isTargetTable(row)) {
						// カラム名を取得
						String columnName = getColumnName(row);
						// カラム定義を取得
						String columnType = getColumnType(row);
						rowValue.add(columnName + " " + columnType);
					}
				}
				sb.add(rowValue.toString());
				sb.add(ddlEnd);
				this.ddlList.add(sb.toString());
			}

		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			LOG.error("excelファイル読込エラー", e);
		}

		ddlList.stream().forEach(System.out::println);
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

	private boolean isTargetTable(Row row) {
		String tableName = row.getCell(1).getStringCellValue();
		return this.tableList.contains(tableName);
	}

}
