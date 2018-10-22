package jp.co.ha.tool.type;

public enum AccessType {

	/** public */
	PUBLIC("public"),
	/** protected */
	PROTECTED("protected"),
	/** デフォルト */
	DEFAULT(""),
	/** private */
	PRIVATE("private");

	private AccessType(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return this.value;
	}
}
