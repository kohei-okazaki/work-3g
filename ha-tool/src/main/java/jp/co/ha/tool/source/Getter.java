package jp.co.ha.tool.source;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.type.AccessType;

public class Getter extends Method {

	private static final String PREFIX = "get";

	/**
	 * コンストラクタ<br>
	 *
	 * @param field
	 *     Field情報
	 */
	public Getter(Field field) {
		super(field, AccessType.PUBLIC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {

		final String TAB = "	";

		StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
		body.add(TAB + accessType.getValue() + " " + field.getClassType().getSimpleName() + " " + getMethodName() + "() {");
		body.add(TAB + TAB + "return " + field.getName() + ";");
		body.add(TAB + "}");

		return body.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getMethodName() {
		return PREFIX + StringUtils.capitalize(field.getName());
	}
}
