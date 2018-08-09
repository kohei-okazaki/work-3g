package jp.co.ha.tool.excel;

import jp.co.ha.tool.type.ColumnType;

public class Cell {

	private String value;

	public Cell(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public ColumnType getColumnType() {
		return ColumnType.of(this.value);
	}
}
