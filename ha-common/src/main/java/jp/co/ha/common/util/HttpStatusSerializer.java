package jp.co.ha.common.util;

import org.springframework.http.HttpStatus;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * {@linkplain HttpStatus}のSerializerクラス
 * 
 * @version 1.0.0
 */
public class HttpStatusSerializer extends ValueSerializer<HttpStatus> {

    @Override
    public void serialize(HttpStatus value, JsonGenerator gen, SerializationContext ctxt)
            throws JacksonException {
        if (BeanUtil.isNull(value)) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.value());
        }
    }

}
