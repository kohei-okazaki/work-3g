package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.tool.type.AccessType;

public class Field {

	private String name;
	private String comment;
	private Class<?> classType;
	private AccessType accessType;

	public Field(String name, String comment, Class<?> classType) {
		this(name, comment, classType, AccessType.PRIVATE);
	}

	public Field(String name, String comment, Class<?> classType, AccessType accessType) {
		this.name = name;
		this.comment = comment;
		this.classType = classType;
		this.accessType = accessType;
	}

	@Override
	public String toString() {

		final String TAB = "	";

		String javadocPrefix = "/**";
		String javadocSuffix = "*/";
		StringJoiner javadocBody = new StringJoiner(" ");
		javadocBody.add(javadocPrefix);
		javadocBody.add(this.comment);
		javadocBody.add(javadocSuffix);
		String javadoc = TAB + javadocBody.toString();

		String suffix = ";";
		StringJoiner fieldBody = new StringJoiner(" ");
		fieldBody.add(this.accessType.getValue());
		fieldBody.add(this.classType.getSimpleName());
		fieldBody.add(this.name);
		String field = TAB + fieldBody.toString() + suffix;
		return javadoc + "\r\n" + field;
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
