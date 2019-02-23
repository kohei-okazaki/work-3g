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
	 * @param src
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String encode(String src, Charset charset) throws UnsupportedEncodingException ;

	/**
	 * デコードを行う
	 * @param src
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String decode(String src, Charset charset) throws UnsupportedEncodingException ;
}
