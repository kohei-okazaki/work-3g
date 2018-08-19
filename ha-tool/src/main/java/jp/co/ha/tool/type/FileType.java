package jp.co.ha.tool.type;

public enum FileType {

	JAVA(".java"),
	SQL(".sql");

	private String suffix;

	private FileType(String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return this.suffix;
	}
}
