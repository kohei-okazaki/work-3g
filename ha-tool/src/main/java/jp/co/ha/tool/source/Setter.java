package jp.co.ha.tool.source;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.type.AccessType;

/**
 * Setter
 *
 * @param <T>
 *     任意の型
 * @since 1.0
 */
public class Setter<T> extends Method<T> {

	/** 接頭語 */
	private static final String PREFIX = "set";

	/**
	 * コンストラクタ
	 *
	 * @param field
	 *     Field
	 */
	public Setter(Field<T> field) {
		this(field, AccessType.PUBLIC);
	}

	/**
	 * コンストラクタ
	 *
	 * @param field
	 *     Field
	 * @param accessType
	 *     アクセスタイプ
	 */
	public Setter(Field<T> field, AccessType accessType) {
		super(field, accessType);
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
		return PREFIX + StringUtils.capitalize(field.getName());
	}
}
