package jp.co.ha.tool.build;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.ha.tool.config.ExcelConfig;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.excel.Cell;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
import jp.co.ha.tool.reader.ExcelReader;
import jp.co.ha.tool.source.Field;
import jp.co.ha.tool.source.Getter;
import jp.co.ha.tool.source.Import;
import jp.co.ha.tool.source.JavaSource;
import jp.co.ha.tool.source.Method;
import jp.co.ha.tool.source.Setter;
import jp.co.ha.tool.type.AccessType;
import jp.co.ha.tool.type.CellPositionType;
import jp.co.ha.tool.type.ClassType;
import jp.co.ha.tool.type.ColumnType;

public class EntityBuilder extends BaseBuilder {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public void execute() {

		ExcelConfig excelConf = new ExcelConfig();
		excelConf.setFilePath("META-INF\\DB.xlsx");
		excelConf.setSheetName("TABLE_LIST");
		ExcelReader reader = new ExcelReader(excelConf);

		List<JavaSource> sourceList = new ArrayList<>();

		// Javaファイルを作成
		for (String table : this.tableList) {
			JavaSource source = new JavaSource();
			setCommonInfo(source);
			Excel excel = reader.read();
			excel.activeSheet("TABLE_LIST");
			for (Row row : excel.getRowList()) {
				if (isTargetTable(row, table)) {
					source.setClassName(toJavaFileName(getClassName(row)));
					// fieldの設定
					Field field = new Field(toCamelCase(getFieldName(row)), getClassType(row));
					source.addField(field);

					// import文を設定
					Import im = new Import(field);
					source.addImportMessage(im);

					// setterの設定
					Setter setter = new Setter(field);
					source.addMethod(setter);

					// getterの設定
					Getter getter = new Getter(field);
					source.addMethod(getter);
				}
			}
			for (Field f : source.getFieldList()) {
				System.out.println(f.toString());
			}
			for (Method m : source.getMethodList()) {
				System.out.println(m.toString());
			}

			FileConfig fileConf = new FileConfig();
			fileConf.setFileName(toJavaFileName(table) + ".java");
			fileConf.setOutputPath(super.baseDir + "\\ha-tool\\src\\main\\java\\jp\\co\\ha\\common\\entity");
			fileConf.setData(source.toString());
			new FileFactory().create(fileConf);
		}
	}

	private void setCommonInfo(JavaSource source) {
		source.setPack("package jp.co.ha.common.entity;");
		source.setClassType(ClassType.CLASS);
		source.setAccessType(AccessType.PUBLIC);
	}

	private String getFieldName(Row row) {
		return row.getCell(CellPositionType.COLUMN_NAME).getValue();
	}

	private String toCamelCase(String name) {
		String result = name.toLowerCase();
		while (result.indexOf("_") != -1) {
			int pos = result.indexOf("_");
			String target = result.substring(pos, pos + 2);
			String after = target.replace("_", "").toUpperCase();
			result = result.replaceFirst(target, after);
		}
		return result;
	}

	private String toJavaFileName(String fileName) {

		String result = fileName.toLowerCase();

		// 1文字目を大文字にする
		Character startChar = result.charAt(0);
		Character large = Character.toUpperCase(result.charAt(0));
		result = result.replaceFirst(startChar.toString(), large.toString());

		// _c を Cに変換
		while (result.indexOf("_") != -1) {
			int pos = result.indexOf("_");
			String target = result.substring(pos, pos + 2);
			String after = target.replace("_", "").toUpperCase();
			result = result.replaceFirst(target, after);
		}
		return result;
	}

	private boolean isTargetTable(Row row, String table) {
		Cell cell = row.getCell(CellPositionType.TABLE_NAME);
		return table.equals(cell.getValue());
	}

	private String getClassName(Row row) {
		Cell cell = row.getCell(CellPositionType.TABLE_NAME);
		return cell.getValue();
	}

	private Class<?> getClassType(Row row) {
		String columnType = row.getCell(CellPositionType.COLUMN_TYPE).getValue();
		ColumnType colType = ColumnType.of(columnType);
		return colType.getClassType();
	}

}
