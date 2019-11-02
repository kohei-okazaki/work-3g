package jp.co.ha.tool.source;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import jp.co.ha.common.util.StringUtil;
import jp.co.ha.tool.type.AccessType;

/**
 * Getter
 *
 * @param <T>
 *     任意の型
 * @since 1.0
 */
public class Getter<T> extends Method<T> {

	/** 接頭語 */
	private static final String PREFIX = "get";

	/**
	 * コンストラクタ
	 *
	 * @param field
	 *     Field
	 */
	public Getter(Field<T> field) {
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
	public Getter(Field<T> field, AccessType accessType) {
		super(field, accessType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {

		final String TAB = "	";

		StringJoiner body = new StringJoiner(StringUtil.NEW_LINE);
		body.add(TAB + accessType.getValue() + " " + field.getClassType().getSimpleName() + " " + getMethodName()
				+ "() {");
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
