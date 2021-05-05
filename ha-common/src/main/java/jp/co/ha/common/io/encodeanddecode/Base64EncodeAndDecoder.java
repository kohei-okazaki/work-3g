package jp.co.ha.common.io.encodeanddecode;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.stereotype.Component;

import jp.co.ha.common.type.Charset;

/**
 * Base64エンコード/デコードクラス
 *
 * @version 1.0.0
 * @see jp.co.ha.common.io.encodeanddecode.annotation.Base64
 */
@Component(Base64EncodeAndDecoder.ID)
public class Base64EncodeAndDecoder implements BaseEncodeAndDecoder {

    /** BeanID */
    public static final String ID = "base64EncodeAndDecoder";

    @Override
    public String encode(String src, Charset charset)
            throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(src.getBytes(charset.getValue()));
    }

    @Override
    public String decode(String src, Charset charset)
            throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(src.getBytes(charset.getValue())),
                charset.getValue());
    }

}
