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

	/** 値 */
	private String value;

	/**
	 * コンストラクタ
	 *
	 * @param value
	 *     値
	 */
	private CsvFileChar(String value) {
		this.value = value;
	}

	/**
	 * 値を返す
	 *
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}
}
