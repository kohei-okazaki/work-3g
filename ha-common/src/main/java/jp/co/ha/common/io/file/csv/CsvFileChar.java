package jp.co.ha.common.io.file.csv;

/**
 * CSVファイル囲い文字列挙
 *
 */
public enum CsvFileChar {

	/** シングルクォート */
	SINGLE_QUOTE("\'"),
	/** ダブルクォート */
	DOBBLE_QUOTE("\"");

	private String value;

	private CsvFileChar(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
