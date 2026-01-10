package jp.co.ha.business.api.slack;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import jp.co.ha.business.api.slack.SlackApiComponent.ContentType;

/**
 * {@linkplain ContentType}のDeserializer
 *
 * @version 1.0.0
 */
public class ContentTypeDeserializer extends JsonDeserializer<ContentType> {

    @Override
    public ContentType deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return ContentType.of(parser.getValueAsString("content_type"));
    }

}
