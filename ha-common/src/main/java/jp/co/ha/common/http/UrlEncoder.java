package jp.co.ha.common.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import jp.co.ha.common.type.Charset;

/**
 * URLエンコードクラス
 *
 */
public class UrlEncoder {

	/**
	 * 指定した文字コードでURLエンコードした文字列のOptionalを返す
	 *
	 * @param target
	 *     エンコード対象文字列
	 * @param charset
	 *     文字コード
	 * @return Encode後の文字列
	 * @throws UnsupportedEncodingException 指定した文字コードが不正の場合
	 */
	public static Optional<String> encode(String target, Charset charset) throws UnsupportedEncodingException {
		return Optional.ofNullable(URLEncoder.encode(target, charset.getValue()));
	}
}
