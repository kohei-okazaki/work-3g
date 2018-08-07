package jp.co.ha.tool.build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.tool.read.ExcelReader;
import jp.co.ha.tool.read.PropertyReader;

public class DdlBuilder {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	/** Tableリスト */
	private List<String> tableList;
	/** DDLリスト */
	private List<String> ddlList;

	public DdlBuilder() {
		init();
	}

	private void init() {

		Properties prop = new PropertyReader().getProperty("target.properties");

		String target = prop.getProperty("target");
		if (Objects.nonNull(target)) {
			this.tableList = Stream.of(target.split(",")).collect(Collectors.toList());
		}

		this.ddlList = new ArrayList<>();
	}

	public void execute() {
		StringJoiner sb = new StringJoiner("\r\n");
		try (Workbook workbook = new ExcelReader().getWorkBook()) {

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

		} catch (IOException e) {
			LOG.error("excelファイル読込エラー", e);
		}

		this.ddlList.stream().forEach(System.out::println);
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
