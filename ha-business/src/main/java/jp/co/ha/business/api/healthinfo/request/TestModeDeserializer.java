package jp.co.ha.business.api.healthinfo.request;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import jp.co.ha.business.api.healthinfo.type.TestMode;

/**
 * JSONのテストモード種別のデシリアライズクラス<br>
 * 文字列型のJSONをJavaのクラスに変換する
 *
 * @version 1.0.0
 */
public class TestModeDeserializer extends JsonDeserializer<TestMode> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TestMode deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        return TestMode.of(parser.getValueAsString("testMode"));
    }

}
