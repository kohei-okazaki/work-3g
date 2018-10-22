package jp.co.ha.tool.source;

public class Package {

	/** 値 */
	private String value;

	/**
	 * コンストラクタ<br>
	 *
	 * @param value
	 *     値
	 */
	public Package(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String prefix = "package ";
		String siffix = ";";
		return prefix + this.value + siffix;
	}

}
