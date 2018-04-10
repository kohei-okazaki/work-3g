package jp.co.ha.common.other;

/**
 * OS定義enum
 *
 */
public enum OsDefine {

	MAC("/"),
	WIN("\\");

	private String value;

	private OsDefine(String value) {
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
