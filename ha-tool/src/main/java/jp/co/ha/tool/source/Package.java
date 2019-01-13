package jp.co.ha.tool.source;

/**
 * Package
 *
 */
public class Package {

	/** 値 */
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 */
	public Package(String value) {
		this.value = value;
	}

	/**
	 * valueを返す
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String prefix = "package ";
		String suffix = ";";
		return prefix + this.value + suffix;
	}

}
