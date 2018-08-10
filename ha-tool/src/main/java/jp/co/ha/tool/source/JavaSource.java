package jp.co.ha.tool.source;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.ha.tool.type.AccessType;
import jp.co.ha.tool.type.ClassType;

public class JavaSource {

	private String pack;
	private List<Import> importList;
	private AccessType accessType;
	private ClassType classType;
	private String className;
	private Class<?> extendsClass;
	private List<Class<?>> implInterfaceList;
	private List<Field> fieldList;
	private List<Method> methodList;

	public JavaSource() {
		this.importList = new ArrayList<>();
		this.implInterfaceList = new ArrayList<>();
		this.fieldList = new ArrayList<>();
		this.methodList = new ArrayList<>();
	}

	public String getPack() {
		return pack;
	}

	public void setPack(String pack) {
		this.pack = pack;
	}

	public void addImport(Import im) {
		this.importList.add(im);
	}

	public List<Import> getImportList() {
		return importList;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class<?> getExtendsClass() {
		return extendsClass;
	}

	public void setExtendsClass(Class<?> extendsClass) {
		this.extendsClass = extendsClass;
	}

	public List<Field> getFieldList() {
		return fieldList;
	}

	public void addField(Field field) {
		this.fieldList.add(field);
	}

	public List<Method> getMethodList() {
		return methodList;
	}

	public void addMethod(Method method) {
		this.methodList.add(method);
	}

	public List<Class<?>> getImplInterfaceList() {
		return implInterfaceList;
	}

	public void addImplInterface(Class<?> implInterface) {
		this.implInterfaceList.add(implInterface);
	}

	private List<String> distinctImport() {
		List<String> importMessageList = this.importList.stream()
														.map(e -> e.getClass().getName())
//														.distinct()
														.collect(Collectors.toList());
		return importMessageList;
	}

	private List<String> fotmatImport(List<String> importList) {
		List<String> list = new ArrayList<>();
		for (String im : importList) {
			if (!im.contains("java.lang.")) {
				// import不要の文を排除
				list.add("import " + im + ";");
			}
		}
		return list;
	}

}
