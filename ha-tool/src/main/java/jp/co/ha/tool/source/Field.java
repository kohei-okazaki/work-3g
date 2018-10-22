package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.type.AccessType;

/**
 * フィールド情報
 *
 */
public class Field {

	/** フィールド名 */
	private String name;
	/** コメント */
	private String comment;
	/** 型 */
	private Class<?> classType;
	/** アクセスタイプ */
	private AccessType accessType;

	/**
	 * コンストラクタ<br>
	 *
	 * @param name
	 *     フィールド名
	 * @param comment
	 *     コメント
	 * @param classType
	 *     型
	 */
	public Field(String name, String comment, Class<?> classType) {
		this(name, comment, classType, AccessType.PRIVATE);
	}

	/**
	 * コンストラクタ<br>
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
	public Field(String name, String comment, Class<?> classType, AccessType accessType) {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Class<?> getClassType() {
		return classType;
	}

	public void setClassType(Class<?> classType) {
		this.classType = classType;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

}
