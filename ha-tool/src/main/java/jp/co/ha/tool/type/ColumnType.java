package jp.co.ha.tool.type;

import java.util.Arrays;

public enum ColumnType {
	/** VARCHAR */
	VARCHAR("VARCHAR"),
	/** DATE */
	DATE("VARCHAR"),
	/** TIMESTAMP */
	TIMESTAMP("VARCHAR"),
	/** DOUBLE */
	DOUBLE("VARCHAR"),
	/** INT */
	INT("VARCHAR");

	private ColumnType(String value) {
		this.value = value;
	}

	private String value;

	public static ColumnType of(String value) {
		return Arrays.asList(ColumnType.class.getEnumConstants())
				.stream()
				.filter(e -> e.value.equals(value))
				.findFirst()
				.orElse(null);
	}
}
