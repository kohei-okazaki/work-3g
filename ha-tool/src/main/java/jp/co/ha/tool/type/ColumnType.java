package jp.co.ha.tool.type;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * カラム定義列挙
 *
 */
public enum ColumnType {

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
	 * valueを返す
	 *
	 * @return value
	 */
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
	 * 指定した値と一致するColumnTypeを返す
	 *
	 * @param value
	 *     値
	 * @return ColumnType
	 */
	public static ColumnType of(String value) {
		return Arrays.asList(ColumnType.class.getEnumConstants())
				.stream()
				.filter(e -> e.value.equals(value))
				.findFirst()
				.orElse(null);
	}
}
