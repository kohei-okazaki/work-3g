package jp.co.ha.tool.type;

public enum AccessType {

	PUBLIC("public"),
	PROTECTED("protected"),
	DEFAULT(""),
	PRIVATE("private");

	private AccessType(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return this.value;
	}
}
