package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.tool.type.AccessType;

public class Field {

	private String name;
	private Class<?> classType;
	private AccessType accessType;

	public Field(String name, Class<?> classType) {
		this(name, classType, AccessType.PRIVATE);
	}

	public Field(String name, Class<?> classType, AccessType accessType) {
		this.name = name;
		this.classType = classType;
		this.accessType = accessType;
	}

	@Override
	public String toString() {
		StringJoiner result = new StringJoiner(" ");
		result.add(this.accessType.getValue());
		result.add(this.classType.getSimpleName());
		result.add(this.name);
		return result.toString() + ";";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
