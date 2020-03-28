package jp.co.ha.common.io.encodeanddecode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import jp.co.ha.common.type.Charset;

/**
 * URLエンコード/デコードクラス
 *
 * @version 1.0.0
 */
@Component("urlEncodeAndDecoder")
public class UrlEncodeAndDecoder implements BaseEncodeAndDecoder {

    /**
     * {@inheritDoc}
     */
    @Override
    public String encode(String src, Charset charset)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(src, charset.getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String decode(String src, Charset charset)
            throws UnsupportedEncodingException {
        return URLDecoder.decode(src, charset.getValue());
    }
}
