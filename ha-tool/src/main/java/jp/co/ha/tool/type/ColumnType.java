package jp.co.ha.tool.type;

import java.math.BigDecimal;
import java.util.Date;

import jp.co.ha.common.type.BaseEnum;

/**
 * カラム定義の列挙
 *
 * @since 1.0
 */
public enum ColumnType implements BaseEnum {

	/** VARCHAR */
	VARCHAR("VARCHAR", String.class),
	/** DATE */
	DATE("DATE", Date.class),
	/** TIMESTAMP */
	TIMESTAMP("TIMESTAMP", Date.class),
	/** DOUBLE */
	DECIMAL("DECIMAL", BigDecimal.class),
	/** INT */
	INT("INT", BigDecimal.class);

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 * @param classType
	 *     クラス型
	 */
	private ColumnType(String value, Class<?> classType) {
		this.value = value;
		this.classType = classType;
	}

	/** 値 */
	private String value;
	/** クラス型 */
	private Class<?> classType;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * classTypeを返す
	 *
	 * @return classType
	 */
	public Class<?> getClassType() {
		return classType;
	}

	/**
	 * @see jp.co.ha.common.type.BaseEnum#of(Class, String)
	 * @param value
	 *     値
	 * @return ColumnType
	 */
	public static ColumnType of(String value) {
		return BaseEnum.of(ColumnType.class, value);
	}
}
