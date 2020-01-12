package jp.co.ha.tool.source;

import java.util.ArrayList;
import java.util.List;

import jp.co.ha.tool.type.AccessType;
import jp.co.ha.tool.type.ClassType;

/**
 * Javaファイル情報
 *
 * @since 1.0
 */
public class JavaSource {

	/** パッケージ */
	private Package pack;
	/** import文のリスト */
	private List<Import> importList = new ArrayList<>();
	/** アクセス型 */
	private AccessType accessType;
	/** クラス型 */
	private ClassType classType;
	/** クラス名 */
	private String className;
	/** 継承クラス */
	private Class<?> extendsClass;
	/** 実装インターフェース */
	private List<Class<?>> implInterfaceList = new ArrayList<>();
	/** フィールド情報のリスト */
	private List<Field<?>> fieldList = new ArrayList<>();
	/** メソッドのリスト */
	private List<Method<?>> methodList = new ArrayList<>();

	/**
	 * packを返す
	 *
	 * @return pack
	 */
	public Package getPackage() {
		return pack;
	}

	/**
	 * packを設定する
	 *
	 * @param pack
	 *     パッケージ
	 */
	public void setPackage(Package pack) {
		this.pack = pack;
	}

	/**
	 * importを追加する
	 *
	 * @param im
	 *     import
	 */
	public void addImport(Import im) {
		this.importList.add(im);
	}

	/**
	 * importListを返す
	 *
	 * @return importList
	 */
	public List<Import> getImportList() {
		return importList;
	}

	/**
	 * accessTypeを返す
	 *
	 * @return accessType
	 */
	public AccessType getAccessType() {
		return accessType;
	}

	/**
	 * accessTypeを設定する
	 *
	 * @param accessType
	 *     アクセスタイプ
	 */
	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	/**
	 * classTypeを返す
	 *
	 * @return classType
	 */
	public ClassType getClassType() {
		return classType;
	}

	/**
	 * classTypeを設定する
	 *
	 * @param classType
	 *     クラスタイプ
	 */
	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	/**
	 * classNameを返す
	 *
	 * @return className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * classNameを設定する
	 *
	 * @param className
	 *     クラス名
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * extendsClassを返す
	 *
	 * @return extendsClass
	 */
	public Class<?> getExtendsClass() {
		return extendsClass;
	}

	/**
	 * extendsClassを設定する
	 *
	 * @param extendsClass
	 *     継承クラス
	 */
	public void setExtendsClass(Class<?> extendsClass) {
		this.extendsClass = extendsClass;
	}

	/**
	 * fieldListを返す
	 *
	 * @return fieldList
	 */
	public List<Field<?>> getFieldList() {
		return fieldList;
	}

	/**
	 * fieldを追加する
	 *
	 * @param field
	 *     フィールド
	 */
	public void addField(Field<?> field) {
		this.fieldList.add(field);
	}

	/**
	 * methodListを返す
	 *
	 * @return methodList
	 */
	public List<Method<?>> getMethodList() {
		return methodList;
	}

	/**
	 * methodを追加する
	 *
	 * @param method
	 *     メソッド
	 */
	public void addMethod(Method<?> method) {
		this.methodList.add(method);
	}

	/**
	 * implInterfaceListを返す
	 *
	 * @return implInterfaceList
	 */
	public List<Class<?>> getImplInterfaceList() {
		return implInterfaceList;
	}

	/**
	 * implInterfaceを追加する
	 *
	 * @param implInterface
	 *     継承するインターフェース
	 */
	public void addImplInterface(Class<?> implInterface) {
		this.implInterfaceList.add(implInterface);
	}

}
