package jp.co.ha.tool.source;

public class Package {

	private String value;

	public Package(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		String prefix = "package ";
		String siffix = ";";
		return prefix + this.value + siffix;
	}


}
