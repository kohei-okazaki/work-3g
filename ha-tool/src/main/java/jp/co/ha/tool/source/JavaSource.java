package jp.co.ha.tool.source;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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

	public void addImportMessage(Import importMessage) {
		this.importList.add(importMessage);
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

	@Override
	public String toString() {
		StringJoiner result = new StringJoiner("\r\n");

		result.add(this.pack);
		result.add("\r\n");

		distinctImport(this.importList);
		for (Import im : this.importList) {
			result.add(im.toString());
		}
		result.add(this.accessType.getValue() + " " + this.getClassType().getValue() + " " + this.getClassName() + " {");
		result.add("\r\n");
		for (Field f : this.fieldList) {
			result.add("	" + f.toString());
		}
		result.add("\r\n");
		for (Method m : this.methodList) {
			result.add("	" + m.toString());
		}
		result.add("\r\n");
		result.add("}");

		return result.toString();
	}

	private void distinctImport(List<Import> importList) {
		List<Import> rsList = new ArrayList<>();
	}

}
