package jp.co.ha.common.util;

/**
 * charset列挙<br>
 *
 */
public enum Charset {

	MS_932("MS932"),
	UTF_8("UTF-8");

	/** 名前 */
	private String name;

	/**
	 * コンストラクタ<br>
	 *
	 * @param name
	 *            名前
	 */
	private Charset(String name) {
		this.name = name;
	}

	/**
	 * 名前を返す<br>
	 *
	 * @return 名前
	 */
	public String getName() {
		return this.name;
	}
}
