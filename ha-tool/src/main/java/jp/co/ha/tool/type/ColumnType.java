package jp.co.ha.tool.type;

import java.math.BigDecimal;
import java.util.Arrays;

public enum ColumnType {
	/** VARCHAR */
	VARCHAR("VARCHAR", String.class),
	/** DATE */
	DATE("DATE", java.util.Date.class),
	/** TIMESTAMP */
	TIMESTAMP("TIMESTAMP", java.util.Date.class),
	/** DOUBLE */
	DOUBLE("DOUBLE", BigDecimal.class),
	/** INT */
	INT("INT", BigDecimal.class);

	private ColumnType(String value, Class<?> classType) {
		this.value = value;
		this.classType = classType;
	}

	private String value;
	private Class<?> classType;

	public String getValue() {
		return this.value;
	}

	public Class<?> getClassType() {
		return this.classType;
	}

	public static ColumnType of(String value) {
		return Arrays.asList(ColumnType.class.getEnumConstants())
				.stream()
				.filter(e -> e.value.equals(value))
				.findFirst()
				.orElse(null);
	}
}
