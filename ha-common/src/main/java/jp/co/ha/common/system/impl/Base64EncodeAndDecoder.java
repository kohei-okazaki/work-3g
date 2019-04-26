package jp.co.ha.common.system.impl;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.stereotype.Service;

import jp.co.ha.common.system.BaseEncodeAndDecoder;
import jp.co.ha.common.type.Charset;

/**
 * Base64エンコード/デコードクラス
 *
 */
@Service("base64EncodeAndDecoder")
public class Base64EncodeAndDecoder implements BaseEncodeAndDecoder {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String encode(String src, Charset charset) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(src.getBytes(charset.getValue()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String decode(String src, Charset charset) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(src.getBytes(charset.getValue())), charset.getValue());
	}

}
