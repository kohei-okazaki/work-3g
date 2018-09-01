package jp.co.ha.common.type;

/**
 * charset列挙<br>
 *
 */
public enum Charset {

	/** MS932 */
	MS_932("MS932"),
	/** UTF-8 */
	UTF_8("UTF-8");

	/** 名前 */
	private String name;

	private Charset(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
