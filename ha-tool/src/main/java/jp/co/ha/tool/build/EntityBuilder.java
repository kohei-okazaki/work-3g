package jp.co.ha.tool.build;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

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
					Field field = new Field(toCamelCase(getFieldName(row)), getClassType(row));
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
			fileConf.setFileName(toJavaFileName(table) + ".java");
			fileConf.setData(build(source));
			new FileFactory().create(fileConf);
		}
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
		while (result.indexOf("_") != -1) {
			int pos = result.indexOf("_");
			String target = result.substring(pos, pos + 2);
			String after = target.replace("_", "").toUpperCase();
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
		StringJoiner result = new StringJoiner("\r\n");

		result.add(buildPackage(source));
		result.add("\r\n");

		result.add(buildImport(source.getImportList()));

		result.add(buildClass(source) + " " + buildInterfaces(source.getImplInterfaceList()) + " {");
		result.add("\r\n");
		result.add(buildFields(source.getFieldList()));
		result.add("\r\n");
		result.add(buildMethods(source.getMethodList()));
		result.add("\r\n");
		result.add("}");

		return result.toString();
	}

	private String buildPackage(JavaSource source) {
		return source.getPackage().toString();
	}

	private String buildImport(List<Import> importList) {
		StringJoiner body = new StringJoiner("\r\n");
		importList.stream().forEach(e -> body.add(e.toString()));
		return body.toString();
	}

	private String buildClass(JavaSource source) {
		String accessType = source.getAccessType().getValue();
		String classType = source.getClassType().getValue();
		String className = source.getClassName();
		StringJoiner body = new StringJoiner(" ");
		return body.add(accessType).add(classType).add(className).toString();
	}

	private String buildInterfaces(List<Class<?>> interfaceList) {
		String prefix = "implements ";
		StringJoiner body = new StringJoiner(",");
		interfaceList.stream().forEach(e -> body.add(e.getSimpleName()));
		return prefix + body.toString();
	}

	private String buildFields(List<Field> fieldList) {
		StringJoiner body = new StringJoiner("\r\n");
		fieldList.stream().forEach(e -> body.add(e.toString()));
		return body.toString();
	}

	private String buildMethods(List<Method> methodList) {
		StringJoiner body = new StringJoiner("\r\n");
		methodList.stream().forEach(e -> body.add(e.toString()));
		return body.toString();
	}

}
