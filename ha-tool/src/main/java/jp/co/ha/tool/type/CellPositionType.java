package jp.co.ha.tool.type;

public enum CellPositionType {

	TABLE_NAME_COMMENT(0, "tableNameComment"),
	TABLE_NAME(1, "tableName"),
	PRIMARY_KEY(2, "primaryKey"),
	SEQUENCE(3, "sequence"),
	COLUMN_NAME_COMMENT(4, "columnNameComment"),
	COLUMN_NAME(5, "columnName"),
	COLUMN_TYPE(6, "columnType"),
	COLUMN_SIZE(7, "columnSize"),
	REMARKS(8, "remarks");

	private int position;
	private String headerName;

	private CellPositionType(int position, String headerName) {
		this.position = position;
		this.headerName = headerName;
	}

	public int getPosition() {
		return this.position;
	}

	public String getHeaderName() {
		return this.headerName;
	}
}
