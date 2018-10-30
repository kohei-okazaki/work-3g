package jp.co.ha.common.system.type;

/**
 * ハッシュ生成のアルゴリズム列挙<br>
 *
 */
public enum Algorithm {

	/** SHA-256 */
	SHA_256("SHA-256");

	private Algorithm(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return this.value;
	}
}
