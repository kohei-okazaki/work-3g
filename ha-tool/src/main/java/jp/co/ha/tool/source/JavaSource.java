package jp.co.ha.tool.source;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.tool.type.AccessType;
import jp.co.ha.tool.type.ClassType;

public class JavaSource {

	/** パッケージ情報 */
	private Package pack;
	/** import文のリスト */
	private List<Import> importList;
	/** アクセス型 */
	private AccessType accessType;
	/** クラス型 */
	private ClassType classType;
	/** クラス名 */
	private String className;
	/** 継承クラス */
	private Class<?> extendsClass;
	/** 実装インターフェース */
	private List<Class<?>> implInterfaceList;
	/** フィールド情報のリスト */
	private List<Field> fieldList;
	/** メソッドのリスト */
	private List<Method> methodList;

	/**
	 * コンストラクタ<br>
	 */
	public JavaSource() {
		this.importList = new ArrayList<>();
		this.implInterfaceList = new ArrayList<>();
		this.fieldList = new ArrayList<>();
		this.methodList = new ArrayList<>();
	}

	public Package getPackage() {
		return pack;
	}

	public void setPackage(Package pack) {
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

}
