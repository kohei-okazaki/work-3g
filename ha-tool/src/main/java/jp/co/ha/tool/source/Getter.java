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
		StringJoiner result = new StringJoiner("\r\n");
		result.add(accessType.getValue() + " " + field.getClassType().getSimpleName() + " " + getMethodName() + "() {");
		result.add("	return " + field.getName() + ";");
		result.add("}");
		return result.toString();
	}

	@Override
	protected String getMethodName() {
		Character c = field.getName().charAt(0);
		String methodName = field.getName().replaceFirst(c.toString(), c.toString().toUpperCase());
		return PREFIX + methodName;
	}

}
