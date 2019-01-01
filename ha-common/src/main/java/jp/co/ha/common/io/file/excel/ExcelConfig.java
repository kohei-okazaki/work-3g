package jp.co.ha.common.io.file.excel;

import jp.co.ha.common.type.Charset;

/**
 * Excel設定情報保持クラス
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
	 * fileNameを返す
	 *
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * fileNameを設定する
	 *
	 * @param fileName
	 *     ファイル名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * charsetを返す
	 *
	 * @return charset Charset
	 */
	public Charset getCharsetType() {
		return charset;
	}

	/**
	 * charsetを設定する
	 *
	 * @param charset
	 *     Charset キャラセット
	 */
	public void setCharsetType(Charset charset) {
		this.charset = charset;
	}

	/**
	 * hasHeaderを返す
	 *
	 * @return hasHeader
	 */
	public boolean hasHeader() {
		return hasHeader;
	}

	/**
	 * hasHeaderを設定する
	 *
	 * @param hasHeader
	 *     ヘッダ利用有無
	 */
	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	/**
	 * hasFooterを返す
	 *
	 * @return hasFooter
	 */
	public boolean hasFooter() {
		return hasFooter;
	}

	/**
	 * hasFooterを設定する
	 *
	 * @param hasFooter
	 *     フッター利用有無
	 */
	public void setHasFooter(boolean hasFooter) {
		this.hasFooter = hasFooter;
	}

}
