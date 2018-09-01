package jp.co.ha.common.file.excel;

import jp.co.ha.common.type.Charset;

/**
 * Excel設定情報保持クラス<br>
 *
 */
public class ExcelConfig {

	/** ファイル名 */
	private String fileName;
	/** キャラセット */
	private Charset charset;
	/** ヘッダ有無 */
	private boolean hasHeader;
	/** フッタ有無 */
	private boolean hasFooter;

	/**
	 * fileNameを返す<br>
	 *
	 * @return fileName
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
	 * charsetを返す<br>
	 *
	 * @return charset Charset
	 */
	public Charset getCharsetType() {
		return charset;
	}

	/**
	 * charsetを設定する<br>
	 *
	 * @param charset
	 *     Charset
	 */
	public void setCharsetType(Charset charset) {
		this.charset = charset;
	}

	/**
	 * hasHeaderを返す<br>
	 *
	 * @return hasHeader
	 */
	public boolean hasHeader() {
		return hasHeader;
	}

	/**
	 * hasHeaderを設定する<br>
	 *
	 * @param hasHeader
	 */
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	/**
	 * hasFooterを返す<br>
	 *
	 * @return hasFooter
	 */
	public boolean hasFooter() {
		return hasFooter;
	}

	/**
	 * hasFooterを設定する<br>
	 *
	 * @param hasFooter
	 */
	public void setHasFooter(boolean hasFooter) {
		this.hasFooter = hasFooter;
	}

}
