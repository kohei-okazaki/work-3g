package jp.co.ha.tool.type;

public enum ClassType {

	/** クラス */
	CLASS("class"),
	/** インターフェース */
	INTERFACE("interface"),
	/** アノテーション */
	ANNOTATION("@interface"),
	/** 列挙 */
	ENUM("enum");

	private ClassType(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return this.value;
	}
}
