package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.tool.type.AccessType;

public class Setter extends Method {

	private static final String PREFIX = "set";

	public Setter(Field field) {
		super(field, AccessType.PUBLIC);
	}

	@Override
	public String toString() {
		StringJoiner result = new StringJoiner("\r\n");
		result.add(accessType.getValue() + " void " + getMethodName()
			+ "(" + field.getClassType().getSimpleName() + " " + field.getName() + ") {");
		result.add("	this." + field.getName() + " = " + field.getName() + ";");
		result.add("}");

		return result.toString();
	}

	@Override
	protected String getMethodName() {
		String firstChar = new Character(field.getName().charAt(0)).toString();
		String methodName = field.getName().replaceFirst(firstChar, firstChar.toUpperCase());
		return PREFIX + methodName;
	}
}
