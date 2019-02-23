package jp.co.ha.common.system;

import java.io.UnsupportedEncodingException;

import jp.co.ha.common.type.Charset;

/**
 * エンコード/デコードの基底インターフェース
 *
 */
public interface BaseEncodeAndDecoder {

	/**
	 * エンコードを行う
	 *
	 * @param src
	 *     エンコード対象文字列
	 * @param charset
	 *     文字コード
	 * @return
	 * @throws UnsupportedEncodingException
	 *     指定した文字コードが不正だった場合
	 */
	String encode(String src, Charset charset) throws UnsupportedEncodingException;

	/**
	 * デコードを行う
	 *
	 * @param src
	 *     デコード対象文字列
	 * @param charset
	 *     文字コード
	 * @return
	 * @throws UnsupportedEncodingException
	 *     指定した文字コードが不正だった場合
	 */
	String decode(String src, Charset charset) throws UnsupportedEncodingException;
}
