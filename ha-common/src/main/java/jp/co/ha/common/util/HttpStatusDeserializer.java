package jp.co.ha.common.util;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * {@linkplain HttpStatus}のDeserializerクラス
 * 
 * @version 1.0.0
 */
public class HttpStatusDeserializer extends JsonDeserializer<HttpStatus> {

    @Override
    public HttpStatus deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JacksonException {
        return HttpStatus.valueOf(p.getValueAsInt());
    }

}
