package jp.co.ha.tool.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.config.FileConfig;
import jp.co.ha.tool.excel.Excel;
import jp.co.ha.tool.excel.Row;
import jp.co.ha.tool.factory.FileFactory;
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
import jp.co.ha.tool.type.ExecuteType;
import jp.co.ha.tool.type.FileType;

public class EntityBuilder extends CommonBuilder {

	@Override
	public void execute() {

		Excel excel = super.reader.read();
		excel.activeSheet("TABLE_LIST");

		for (String table : this.targetTableList) {
			JavaSource source = new JavaSource();
			setCommonInfo(source);
			for (Row row : excel.getRowList()) {

				if (isTargetTable(row, table)) {
					source.setClassName(toJavaFileName(getClassName(row)));

					// fieldの設定
					Field field = new Field(toCamelCase(getFieldName(row)), getColumnComment(row), getClassType(row));
					source.addField(field);

					// fieldのimport文を設定
					Import im = new Import(field);
					source.addImport(im);

					// setterの設定
					Setter setter = new Setter(field);
					source.addMethod(setter);

					// getterの設定
					Getter getter = new Getter(field);
					source.addMethod(getter);
				}
			}

			FileConfig fileConf = getFileConfig(ExecuteType.ENTITY);
			fileConf.setFileName(toJavaFileName(table) + FileType.JAVA.getSuffix());
			fileConf.setData(build(source));
			FileFactory.create(fileConf);
		}
	}

	private String getColumnComment(Row row) {
		return row.getCell(CellPositionType.COLUMN_NAME_COMMENT).getValue();
	}

	private void setCommonInfo(JavaSource source) {
		source.setPackage(new jp.co.ha.tool.source.Package("jp.co.ha.business.db.entity"));
		source.setClassType(ClassType.CLASS);
		source.setAccessType(AccessType.PUBLIC);
		source.addImplInterface(Serializable.class);
		source.addImport(new Import(Serializable.class));
	}

	private String getFieldName(Row row) {
		return row.getCell(CellPositionType.COLUMN_NAME).getValue();
	}

	private String toCamelCase(String name) {
		String result = name.toLowerCase();
		while (result.indexOf(StringUtil.UNDER_SCORE) != -1) {
			int pos = result.indexOf(StringUtil.UNDER_SCORE);
			String target = result.substring(pos, pos + 2);
			String after = target.replace(StringUtil.UNDER_SCORE, StringUtil.EMPTY).toUpperCase();
			result = result.replaceFirst(target, after);
		}
		return result;
	}

	private String toJavaFileName(String fileName) {

		String result = toCamelCase(fileName);
		Character startChar = result.charAt(0);
		Character large = Character.toUpperCase(result.charAt(0));
		return result.replaceFirst(startChar.toString(), large.toString());
	}

	private String getClassName(Row row) {
		return row.getCell(CellPositionType.PHYSICAL_NAME).getValue();
	}

	private Class<?> getClassType(Row row) {
		String columnType = row.getCell(CellPositionType.COLUMN_TYPE).getValue();
		return ColumnType.of(columnType).getClassType();
	}

	private String build(JavaSource source) {
		StringBuilder result = new StringBuilder();

		result.append(buildPackage(source)).append(StringUtil.CRLF + StringUtil.CRLF);

		result.append(buildImport(source.getImportList())).append(StringUtil.CRLF + StringUtil.CRLF);

		result.append(buildClass(source) + " " + buildInterfaces(source.getImplInterfaceList()) + " {").append(StringUtil.CRLF + StringUtil.CRLF);

		result.append(buildFields(source.getFieldList())).append(StringUtil.CRLF + StringUtil.CRLF);

		result.append(buildMethods(source.getMethodList())).append(StringUtil.CRLF + StringUtil.CRLF);

		result.append("}");

		return result.toString();
	}

	private String buildPackage(JavaSource source) {
		return source.getPackage().toString();
	}

	private String buildImport(List<Import> importList) {

		List<String> strImportList = new ArrayList<>();
		importList.stream()
					.filter(e -> (!strImportList.contains(e.toString())))
					.map(e -> e.toString())
					.forEach(e -> strImportList.add(e));

		StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
		strImportList.stream().forEach(e -> body.add(e));
		return body.toString();
	}

	private String buildClass(JavaSource source) {
		String accessType = source.getAccessType().getValue();
		String classType = source.getClassType().getValue();
		String className = source.getClassName();
		StringJoiner body = new StringJoiner(StringUtil.SPACE);
		return body.add(accessType).add(classType).add(className).toString();
	}

	private String buildInterfaces(List<Class<?>> interfaceList) {
		String prefix = "implements ";
		StringJoiner body = new StringJoiner(StringUtil.COMMA);
		interfaceList.stream().forEach(e -> body.add(e.getSimpleName()));
		return prefix + body.toString();
	}

	private String buildFields(List<Field> fieldList) {
		StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
		fieldList.stream().forEach(e -> body.add(e.toString()));
		return body.toString();
	}

	private String buildMethods(List<Method> methodList) {
		StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
		methodList.stream().forEach(e -> body.add(e.toString()));
		return body.toString();
	}

}
