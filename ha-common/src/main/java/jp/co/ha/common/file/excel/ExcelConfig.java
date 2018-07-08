package jp.co.ha.common.file.excel;

import jp.co.ha.common.util.Charset;

/**
 * Excel設定情報保持クラス<br>
 *
 */
public class ExcelConfig {

	/** キャラセット */
	private Charset charset;
	/** ヘッダ有無 */
	private boolean hasHeader;
	/** フッタ有無 */
	private boolean hasFooter;

	/**
	 * charsetを返す<br>
	 *
	 * @return charset Charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * charsetを設定する<br>
	 *
	 * @param charset
	 *     Charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * hasHeaderを返す<br>
	 *
	 * @return hasHeader
	 */
	public boolean isHasHeader() {
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
	public boolean isHasFooter() {
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
