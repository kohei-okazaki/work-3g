package jp.co.ha.tool.type;

public enum PropertyType {

	/** 対象テーブル */
	TARGET_TABLE("targetTable"),
	/** 基底ディレクトリ */
	BASE_DIR("baseDir");

	private PropertyType(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return this.value;
	}
}
