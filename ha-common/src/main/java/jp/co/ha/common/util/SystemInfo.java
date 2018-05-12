package jp.co.ha.common.util;

/**
 * OS定義列挙<br>
 *
 */
public enum SystemInfo {

	MAC("/"),
	WIN("\\");

	private String value;

	private SystemInfo(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static boolean isWin() {
		return WIN.getValue().equals(System.getProperty("file.separator"));
	}

	public static boolean isMac() {
		return MAC.getValue().equals(System.getProperty("file.separator"));
	}

}
