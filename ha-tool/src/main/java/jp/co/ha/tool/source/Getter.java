package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.tool.type.AccessType;

public class Getter extends Method {

	private static final String PREFIX = "get";

	public Getter(Field field) {
		super(field, AccessType.PUBLIC);
	}

	@Override
	public String toString() {
		StringJoiner body = new StringJoiner("\r\n");
		body.add(accessType.getValue() + " " + field.getClassType().getSimpleName() + " " + getMethodName() + "() {");
		body.add("	return " + field.getName() + ";");
		body.add("}");
		return body.toString();
	}

	@Override
	protected String getMethodName() {
		String firstChar = new Character(field.getName().charAt(0)).toString();
		String methodName = field.getName().replaceFirst(firstChar, firstChar.toUpperCase());
		return PREFIX + methodName;
	}
}
