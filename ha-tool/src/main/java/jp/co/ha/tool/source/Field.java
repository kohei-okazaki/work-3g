package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.type.AccessType;

/**
 * フィールド情報
 *
 * @param <T>
 *     任意の型
 *
 */
public class Field<T> {

	/** フィールド名 */
	private String name;
	/** コメント */
	private String comment;
	/** 型 */
	private Class<T> classType;
	/** アクセスタイプ */
	private AccessType accessType;

	/**
	 * コンストラクタ
	 *
	 * @param name
	 *     フィールド名
	 * @param comment
	 *     コメント
	 * @param classType
	 *     型
	 */
	public Field(String name, String comment, Class<T> classType) {
		this(name, comment, classType, AccessType.PRIVATE);
	}

	/**
	 * コンストラクタ
	 *
	 * @param name
	 *     フィールド名
	 * @param comment
	 *     コメント
	 * @param classType
	 *     型
	 * @param accessType
	 *     アクセスタイプ
	 */
	public Field(String name, String comment, Class<T> classType, AccessType accessType) {
		this.name = name;
		this.comment = comment;
		this.classType = classType;
		this.accessType = accessType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {

		final String TAB = "	";

		String javadocPrefix = "/**";
		String javadocSuffix = "*/";
		StringJoiner javadocBody = new StringJoiner(StringUtil.SPACE);
		javadocBody.add(javadocPrefix);
		javadocBody.add(this.comment);
		javadocBody.add(javadocSuffix);
		String javadoc = TAB + javadocBody.toString();

		String suffix = ";";
		StringJoiner fieldBody = new StringJoiner(StringUtil.SPACE);
		fieldBody.add(this.accessType.getValue());
		fieldBody.add(this.classType.getSimpleName());
		fieldBody.add(this.name);
		String field = TAB + fieldBody.toString() + suffix;
		return javadoc + StringUtil.NEW_LINE + field;
	}

	/**
	 * nameを返す
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する
	 *
	 * @param name
	 *     名前
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * commentを返す
	 *
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * commentを設定する
	 *
	 * @param comment
	 *     コメント
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * classTypeを返す
	 *
	 * @return classType
	 */
	public Class<?> getClassType() {
		return classType;
	}

	/**
	 * classTypeを設定する
	 *
	 * @param classType
	 *     クラス型
	 */
	public void setClassType(Class<T> classType) {
		this.classType = classType;
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

}
