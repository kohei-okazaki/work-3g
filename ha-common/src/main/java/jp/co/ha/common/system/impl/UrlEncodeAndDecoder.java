package jp.co.ha.common.system.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import jp.co.ha.common.system.BaseEncodeAndDecoder;
import jp.co.ha.common.type.Charset;

/**
 * URLエンコード/デコードクラス
 *
 */
@Service("urlEncodeAndDecoder")
public class UrlEncodeAndDecoder implements BaseEncodeAndDecoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encode(String src, Charset charset) throws UnsupportedEncodingException {
		return URLEncoder.encode(src, charset.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String decode(String src, Charset charset) throws UnsupportedEncodingException {
		return URLDecoder.decode(src, charset.getValue());
	}
}
