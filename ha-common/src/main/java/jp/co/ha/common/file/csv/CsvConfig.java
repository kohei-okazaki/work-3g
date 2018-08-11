package jp.co.ha.common.file.csv;

import jp.co.ha.common.type.CharsetType;

/**
 * CSV設定情報保持クラス<br>
 * 今後、追加するCSV設定情報は本クラスで定義する<br>
 *
 */
public class CsvConfig {

	/** ファイル名 */
	private String fileName;
	/** ヘッダ有無 */
	private boolean hasHeader;
	/** 囲い文字有無 */
	private boolean hasEnclosure;
	/** 囲い文字 */
	private CsvFileChar csvFileChar;
	/** 文字コード */
	private CharsetType charset;
	/** フッタ有無 */
	private boolean hasFooter;
	/** マスク利用有無 */
	private boolean useMask;

	/**
	 * fileNameを返す<br>
	 *
	 * @return fileName ファイル名
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * fileNameを設定する<br>
	 *
	 * @param fileName
	 *     ファイル名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * hasHeaderを返す<br>
	 *
	 * @return hasHeader ヘッダー有無
	 */
	public boolean hasHeader() {
		return hasHeader;
	}

	/**
	 * hasHeaderを設定する<br>
	 *
	 * @param hasHeader
	 *     ヘッダー有無
	 */
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	/**
	 * hasEnclosureを返す<br>
	 *
	 * @return hasEnclosure 囲い文字有無
	 */
	public boolean hasEnclosure() {
		return hasEnclosure;
	}

	/**
	 * hasEnclosureを設定する<br>
	 *
	 * @param hasEnclosure
	 *     囲い文字有無
	 */
	public void setHasEnclosure(boolean hasEnclosure) {
		this.hasEnclosure = hasEnclosure;
	}

	/**
	 * csvFileCharを返す<br>
	 *
	 * @return csvFileChar CSVファイル囲い文字列挙
	 */
	public CsvFileChar getCsvFileChar() {
		return csvFileChar;
	}

	/**
	 * csvFileCharを設定する<br>
	 *
	 * @param csvFileChar
	 *     CSVファイル囲い文字列挙
	 */
	public void setCsvFileChar(CsvFileChar csvFileChar) {
		this.csvFileChar = csvFileChar;
	}

	/**
	 * charsetを返す<br>
	 *
	 * @return charset 文字コード
	 */
	public CharsetType getCharset() {
		return charset;
	}

	/**
	 * charsetを設定する<br>
	 *
	 * @param charset
	 *     文字コード
	 */
	public void setCharset(CharsetType charset) {
		this.charset = charset;
	}

	/**
	 * hasFooterを返す<br>
	 *
	 * @return hasFooter フッタ有無
	 */
	public boolean hasFooter() {
		return hasFooter;
	}

	/**
	 * hasFooterを設定する<br>
	 *
	 * @param hasFooter
	 *     フッタ有無
	 */
	public void setHasFooter(boolean hasFooter) {
		this.hasFooter = hasFooter;
	}

	/**
	 * useMaskを返す<br>
	 *
	 * @return useMask
	 */
	public boolean useMask() {
		return useMask;
	}

	/**
	 * useMaskを設定する<br>
	 *
	 * @param useMask
	 *     マスク利用有無
	 */
	public void setUseMask(boolean useMask) {
		this.useMask = useMask;
	}

}
