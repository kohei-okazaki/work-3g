package jp.co.ha.common.other;

/**
 * キャラセットEnum<br>
 *
 */
public enum Charset {

	MS_932("MS932"),
	UTF_8("UTF-8");

	private String name;

	private Charset(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
