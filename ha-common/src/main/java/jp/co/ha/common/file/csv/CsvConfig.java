package jp.co.ha.common.file.csv;

import jp.co.ha.common.util.Charset;

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
	private String enclosureChar;
	/** 文字コード */
	private Charset charset;
	/** フッタ有無 */
	private boolean hasFooter;

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
	 * enclosureCharを返す<br>
	 *
	 * @return enclosureChar 囲い文字
	 */
	public String getEnclosureChar() {
		return enclosureChar;
	}

	/**
	 * enclosureCharを設定する<br>
	 *
	 * @param enclosureChar
	 *     囲い文字
	 */
	public void setEnclosureChar(String enclosureChar) {
		this.enclosureChar = enclosureChar;
	}

	/**
	 * charsetを返す<br>
	 *
	 * @return charset 文字コード
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * charsetを設定する<br>
	 *
	 * @param charset
	 *     文字コード
	 */
	public void setCharset(Charset charset) {
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

}
