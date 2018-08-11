package jp.co.ha.common.type;

import java.util.Arrays;

/**
 * ファイル列挙
 *
 */
public enum FileType {

	HTML("html", ".html"),
	JAVA_SCRIPT("js", ".js"),
	CSS("css", ".css");

	private FileType(String value, String suffix) {
		this.value = value;
		this.suffix = suffix;
	}

	private String value;
	private String suffix;

	public String getValue() {
		return this.value;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public static FileType of(String suffix) {
		return Arrays.asList(FileType.class.getEnumConstants())
				.stream()
				.filter(e -> e.getSuffix().equals(suffix))
				.findFirst()
				.orElse(null);
	}
}
