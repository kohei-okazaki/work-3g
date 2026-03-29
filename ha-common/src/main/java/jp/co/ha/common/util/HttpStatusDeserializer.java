package jp.co.ha.common.util;

import org.springframework.http.HttpStatus;

import jp.co.ha.common.log.Logger;
import jp.co.ha.common.log.LoggerFactory;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

/**
 * {@linkplain HttpStatus}のDeserializerクラス
 *
 * @version 1.0.0
 */
public class HttpStatusDeserializer extends ValueDeserializer<HttpStatus> {
    /** LOG */
    private static final Logger LOG = LoggerFactory
            .getLogger(HttpStatusDeserializer.class);

    @Override
    public HttpStatus deserialize(JsonParser parser, DeserializationContext ctxt) {
        LOG.debug("HttpStatusDeserializer called1. raw=" + parser.getValueAsInt());
        HttpStatus st = HttpStatus.valueOf(parser.getValueAsInt());
        LOG.debug("HttpStatusDeserializer called2. st=" + st);
        return st;
    }
}