package jp.co.ha.tool.source;

import java.util.StringJoiner;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.type.AccessType;

public class Setter extends Method {

	private static final String PREFIX = "set";

	/**
	 * コンストラクタ<br>
	 *
	 * @param field
	 *     Field情報
	 */
	public Setter(Field field) {
		super(field, AccessType.PUBLIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {

		final String TAB = "	";

		StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
		body.add(TAB + accessType.getValue() + " void " + getMethodName()
			+ "(" + field.getClassType().getSimpleName() + " " + field.getName() + ") {");
		body.add(TAB + TAB + "this." + field.getName() + " = " + field.getName() + ";");
		body.add(TAB + "}");

		return body.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getMethodName() {
		String firstChar = Character.valueOf(field.getName().charAt(0)).toString();
		String methodName = field.getName().replaceFirst(firstChar, firstChar.toUpperCase());
		return PREFIX + methodName;
	}
}
