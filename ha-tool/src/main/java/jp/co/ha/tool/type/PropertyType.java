package jp.co.ha.tool.type;

public enum PropertyType {

	TARGET_TABLE("targetTable"),
	BASE_DIR("baseDir")
	;
	private PropertyType(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return this.value;
	}
}
