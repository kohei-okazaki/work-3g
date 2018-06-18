package jp.co.ha.common.file.excel;

import jp.co.ha.common.util.Charset;

/**
 * Excel設定情報保持クラス<br>
 *
 */
public class ExcelConfig {

	/** キャラセット */
	private Charset charset;

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
	 *            Charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

}
