package jp.co.ha.common.http;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

import jp.co.ha.common.type.Charset;

/**
 * URLデコードクラス
 *
 */
public class UrlDecoder {

	/**
	 * 指定した文字コードでURLデコードした文字列のOptionalを返す
	 *
	 * @param target
	 *     エンコード対象文字列
	 * @param charset
	 *     文字コード
	 * @return Decode後の文字列
	 * @throws UnsupportedEncodingException
	 *     指定した文字コードが不正の場合
	 */
	public static Optional<String> decode(String target, Charset charset) throws UnsupportedEncodingException {
		return Optional.ofNullable(URLDecoder.decode(target, charset.getValue()));
	}

}
