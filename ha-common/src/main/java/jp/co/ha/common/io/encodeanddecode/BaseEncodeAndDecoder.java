package jp.co.ha.common.io.encodeanddecode;

import java.io.UnsupportedEncodingException;

import jp.co.ha.common.type.Charset;

/**
 * エンコード/デコードの基底インターフェース
 *
 * @since 1.0
 */
public interface BaseEncodeAndDecoder {

	/**
	 * エンコードを行う
	 *
	 * @param src
	 *     エンコード対象文字列
	 * @param charset
	 *     文字コード
	 * @return エンコード後の文字列
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
	 * @return デコード後の文字列
	 * @throws UnsupportedEncodingException
	 *     指定した文字コードが不正だった場合
	 */
	String decode(String src, Charset charset) throws UnsupportedEncodingException;
}
