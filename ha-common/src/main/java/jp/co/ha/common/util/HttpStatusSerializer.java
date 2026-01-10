package jp.co.ha.common.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * {@linkplain HttpStatus}のSerializerクラス
 * 
 * @version 1.0.0
 */
public class HttpStatusSerializer extends JsonSerializer<HttpStatus> {

    @Override
    public void serialize(HttpStatus value, JsonGenerator gen,
            SerializerProvider serializers) throws IOException {
        if (BeanUtil.isNull(value)) {
            gen.writeNull();
        } else {
            gen.writeNumber(value.value());
        }
    }

}
